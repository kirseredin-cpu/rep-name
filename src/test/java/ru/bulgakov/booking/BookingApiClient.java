package ru.bulgakov.booking;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.bulgakov.booking.config.BookingConfig;
import ru.bulgakov.booking.dto.AuthRequest;
import ru.bulgakov.booking.dto.AuthResponse;
import ru.bulgakov.booking.dto.BookingDTO;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.bulgakov.booking.config.BoookingApiConfig.getBookingConfig;

public class BookingApiClient {
    private static final BookingConfig CFG = getBookingConfig();

    private final RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(CFG.bookingUrl())
            .setContentType(ContentType.JSON)
            .build();

    public Response auth(String user, String password) {
        return given(spec)
                .body(new AuthRequest(user, password))
                .post( "/auth")
                .then()
                .log().status()
                .extract().response();
    }
    public Response auth(Object payload) {
        var request = given(spec)
                .contentType(ContentType.JSON);

        if (payload != null) {
            request.body(payload); // RestAssured сам сериализует AuthRequest или отправит строку "{}"
        }
        // если payload == null → .body() не вызывается (сценарий 6)

        return request.post("/auth")
                .then()
                .log().status()
                .extract().response();
    }
    public Response authWithoutBody() {
        return given(spec)
                .post( "/auth")
                .then()
                .log().status()
                .extract().response();
    }
    public Response getBooking(Integer id) {
        return given(spec)
                .pathParam("BOOKING_ID", id)
                .get( "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }
    public Response getBookings(Map<String, Object> queryParams) {
        return given(spec)
                .queryParams(queryParams)
                .log().params()
                .get( "/booking/")
                .then()
                .extract().response();
    }

    public Response createBooking(BookingDTO bookingDTO) {
        return given(spec)
                .body(bookingDTO)
                .post( "/booking")
                .then()
                .extract().response();
    }

        public Response createBooking(Object payload) {
            var request = given(spec).contentType(ContentType.JSON);

            if (payload != null) {
                request.body(payload); // Jackson сериализует BookingRequest
            }
            // если payload == null → .body() не вызывается (сценарий "пустое тело")

            return request.post("/booking")
                    .then().log().status()
                    .extract().response();
        }
    public Response updateBooking(BookingDTO bookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(bookingDTO)
                .pathParam("BOOKING_ID", id)
                .put( "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }
    public Response partialUpdateBooking(BookingDTO bookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(bookingDTO)
                .pathParam("BOOKING_ID", id)
                .patch( "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }
    public Response deleteBooking(Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .pathParam("BOOKING_ID", id)
                .delete( "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }
    private String getToken() {
        return auth(CFG.username(), CFG.password()).as(AuthResponse.class).getToken();
    }
}
