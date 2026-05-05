package ru.bulgakov.booking;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bulgakov.booking.config.BookingConfig;
import ru.bulgakov.booking.dto.*;
import ru.bulgakov.booking.steps.BookingSteps;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.bulgakov.booking.config.BoookingApiConfig.getBookingConfig;
import static ru.bulgakov.booking.steps.BookingSteps.randomBooking;

public class BookingTest extends BaseApiTest {
    private static final Faker faker = new Faker();
    private static final BookingConfig CFG = getBookingConfig();


    private final BookingApiClient bookingClient = new BookingApiClient();
    private final BookingSteps bookingSteps = new BookingSteps();

    //Arrange - act - assert
    @Test
    void authTest() {
        Response resp = bookingClient.auth(CFG.username(), CFG.password());
        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidAuthTestCases")
    void negativeAuthTest(String scenario, Object body) {
        Response resp = bookingClient.auth(body);

        assertThat(resp.statusCode()).isEqualTo(200);

        // ✅ Безопасная проверка токена.
        // resp.as(AuthResponse.class) может выбросить JsonMappingException на 4xx,
        // если сервер возвращает HTML, plain text или другой формат ошибки.
        String token = resp.jsonPath().getString("token");
        assertThat(token).as("Токен не должен возвращаться: %s", scenario).isNull();
    }

    static Stream<Arguments> invalidAuthTestCases() {
        return Stream.of(
                Arguments.of("1. Неверный пароль", new AuthRequest("admin", "password1234")),
                Arguments.of("2. Неверный логин", new AuthRequest("admin1", "password123")),
                Arguments.of("3. Пустой пароль", new AuthRequest("admin", "")),
                Arguments.of("4. Пустой логин", new AuthRequest("", "password123")),
                Arguments.of("5. Пустое body ({})", "{}"),
                Arguments.of("6. Без body вообще", null)
        );
    }


    @Test
    void createBookingTest() {
        BookingDTO bookingDTO = randomBooking();
        Response resp =
                bookingClient.createBooking(bookingDTO);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        CreateBookingResponse createBookingResponse = resp.as(CreateBookingResponse.class);
        BookingSteps.bookingsShouldBeEqual(bookingDTO, createBookingResponse.getBooking());
        assertThat(createBookingResponse.getBookingid()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidCreateBookingTestCases")
    void negativeCreateBookingTest(String firstname, String lastname, Integer totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        BookingDTO bookingDTO = new BookingDTO(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
        Response resp =
                bookingClient.createBooking(bookingDTO);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        CreateBookingResponse createBookingResponse = resp.as(CreateBookingResponse.class);
        BookingSteps.bookingsShouldBeEqual(bookingDTO, createBookingResponse.getBooking());
        assertThat(createBookingResponse.getBookingid()).isNotNull();
    }

    static Stream<Arguments> invalidCreateBookingTestCases() {
        return Stream.of(
                Arguments.of("", "lastname", 5, true, "2026-02-03", "2027-02-03", "Doom"),
                Arguments.of("firstname", "", 5, true, "2026-02-03", "2027-02-03", "Doom"),
                Arguments.of("firstname", "lastname", -500, true, "2026-02-03", "2027-02-03", "Doom"),
                Arguments.of("firstname", "lastname", 500, true, "2027-02-03", "2026-02-03", "Doom"),
                Arguments.of("firstname", "lastname", 500, true, "date1", "date2", "Doom"));
    }

    @Test
    void updateBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        BookingDTO bookingDTO = randomBooking();
        Response updateResponse = bookingClient.updateBooking(bookingDTO, bookingId);
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDTO = updateResponse.as(BookingDTO.class);
        BookingSteps.bookingsShouldBeEqual(bookingDTO, updatedBookingDTO);
        //авторизация -> создать букинг -> его обновить
    }

    @Test
    void partialUpdateBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        BookingDTO bookingDTO = new BookingDTO(
                faker.football().players(),
                faker.number().numberBetween(10001, 12000),
                "2026-02-03");

        Response updateResponse = bookingClient.partialUpdateBooking(bookingDTO, bookingId);
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDTO = updateResponse.as(BookingDTO.class);
        assertThat(bookingDTO.getFirstname()).isEqualTo(updatedBookingDTO.getFirstname());
        assertThat(bookingDTO.getTotalprice()).isEqualTo(updatedBookingDTO.getTotalprice());
        assertThat(bookingDTO.getBookingdates().getCheckin()).isEqualTo(updatedBookingDTO.getBookingdates().getCheckin());
        //авторизация -> создать букинг -> его обновить
    }

    @Test
    void getBookingTest() {
        CreateBookingResponse booking = bookingSteps.createBooking();

        Response resp = bookingClient.getBooking(booking.getBookingid());
        assertThat(resp.getStatusCode()).isEqualTo(200);

        BookingSteps.bookingsShouldBeEqual(booking.getBooking(), resp.as(BookingDTO.class));
    }

    @Test
    void getBookingsByLastName() {
        int bookingQuantity = 5;

        String lastName = faker.name().lastName();

        List<Integer> bookingIds = bookingSteps.generateBookings(bookingQuantity, lastName);

        Response resp = bookingClient.getBookings(Map.of("lastname", lastName));
        assertThat(resp.getStatusCode()).isEqualTo(200);

        List<BookingId> bookings = resp.as(new TypeRef<List<BookingId>>() {
        });
        assertThat(bookings)
                .hasSize(bookingQuantity)
                .doesNotHaveDuplicates()
                .doesNotContainNull()
                .extracting(booking -> booking.bookingid())
                .containsExactlyInAnyOrderElementsOf(bookingIds);
    }


    @Test
    void deleteBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        Response deleteResp = bookingClient.deleteBooking(bookingId);
        assertThat(deleteResp.getStatusCode()).isEqualTo(201);

        Response getResp = bookingClient.getBooking(bookingId);
        assertThat(getResp.getStatusCode()).isEqualTo(404);

    }


}

/*private static CreateBookingDTO bookingRequest() {
    CreateBookingDTO booking = new CreateBookingDTO();
    booking.setFirstname("da");
    booking.setLastname("da");
    booking.setBookingdates(new CreateBookingDTO.BookingDates("2026-03-04", "2028-03-04"));

    return booking;
}*/
