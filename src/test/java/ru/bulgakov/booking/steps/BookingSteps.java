package ru.bulgakov.booking.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import net.datafaker.Faker;
import ru.bulgakov.booking.BookingApiClient;
import ru.bulgakov.booking.dto.BookingDTO;
import ru.bulgakov.booking.dto.CreateBookingResponse;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingSteps {
    private static final Faker faker = new Faker();
    private final BookingApiClient bookingClient = new BookingApiClient();


    public CreateBookingResponse createBooking() {
        return createBooking(randomBooking());
    }

    public CreateBookingResponse createBooking(BookingDTO booking) {
        Response createResp = bookingClient.createBooking(booking);
        step("Проверка, Статус код = 200", () ->
    assertThat(createResp.getStatusCode()).isEqualTo(200));

        return createResp.as(CreateBookingResponse.class);
    }

    @Step("Booking Generation {bookingQuantity}")
    public List<Integer> generateBookings(int bookingQuantity, String lastName) {
        List<Integer> bookingIds = new ArrayList<>();
        for (int i = 0; i < bookingQuantity; i++) {
            BookingDTO bookingDTO = randomBooking();
            bookingDTO.setLastname(lastName);

            Integer bookingId = createBooking(bookingDTO).getBookingid();
            bookingIds.add(bookingId);
        }
        return bookingIds;
    }
    @Step("Проверить соотвествие всех полей в ответе")
    public static void bookingsShouldBeEqual(BookingDTO expected, BookingDTO actual) {
        assertAll(
                () -> assertThat(actual.getFirstname()).as("Firstname difference").isEqualTo(expected.getFirstname()),
                () -> assertThat(actual.getLastname()).isEqualTo(expected.getLastname()),
                () -> assertThat(actual.getTotalprice()).isEqualTo(expected.getTotalprice()),
                () -> assertThat(actual.getDepositpaid()).isEqualTo(expected.getDepositpaid()),
                () -> assertThat(actual.getAdditionalneeds()).isEqualTo(expected.getAdditionalneeds()),
                () -> assertThat(actual.getBookingdates()).isNotNull(),
                () -> assertThat(actual.getBookingdates().getCheckin()).isEqualTo(expected.getBookingdates().getCheckin()),
                () -> assertThat(actual.getBookingdates().getCheckout()).isEqualTo(expected.getBookingdates().getCheckout())

        );
    }


    public static BookingDTO randomBooking() {
        return BookingDTO.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1000, 10000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDTO.BookingDates.builder()
                        .checkin("2026-04-05")
                        .checkout("2027-05-03")
                        .build())
                .additionalneeds(faker.videoGame().title())
                .build();
    }
}
