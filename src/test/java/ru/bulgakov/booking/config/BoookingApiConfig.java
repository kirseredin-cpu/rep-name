package ru.bulgakov.booking.config;

import org.aeonbits.owner.ConfigFactory;
import ru.bulgakov.webshop.config.WebDriverConfig;

public class BoookingApiConfig {

    private static final BookingConfig config = ConfigFactory.create(BookingConfig.class, System.getProperties());

    public static BookingConfig getBookingConfig() {
        return config;
    }
}
