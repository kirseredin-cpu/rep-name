package ru.bulgakov.webshop.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class APITest {
    @Test
    @Tags({@Tag("API"), @Tag("positive")})
    void apitest1() {
        System.out.println("API pos");
    }

    @Test
    @Tags({@Tag("API"), @Tag("negative")})
    void apitest2() {
        System.out.println("API neg");
    }
}
