package ru.bulgakov.booking.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;

    public BookingDTO(String firstname, Integer totalprice, String checkin) {
        this.firstname= firstname;
        this.totalprice= totalprice;
        this.bookingdates = new BookingDates();
        this.bookingdates.checkin = checkin;
    }
    public BookingDTO(String firstname,String lastname, Integer totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        this.firstname= firstname;
        this.lastname= lastname;
        this.totalprice= totalprice;
        this.depositpaid= depositpaid;
        this.bookingdates = new BookingDates();
        this.bookingdates.checkin = checkin;
        this.bookingdates.checkout = checkout;
        this.additionalneeds= additionalneeds;

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookingDates {
        private String checkin;
        private String checkout;    }
}
