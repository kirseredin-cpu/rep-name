package ru.bulgakov.webshop.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class JenkinsTest {
    @Test
    @Tags({@Tag("UI"), @Tag("positive")})
    @DisplayName("UI positive")
    void jenkkinsTest1() {
        System.out.println("Ui positive test");
    }
    @Test
    @Tags({@Tag("UI"), @Tag("negative")})
    @DisplayName("UI negative")
    void jenkkinsTest2() {
        System.out.println("Ui negative test");

    }
    @Test
    @Tags({@Tag("API"), @Tag("positive")})
    @DisplayName("API positive")
    void jenkkinsTest3() {
        System.out.println("API positive test");

    }
    @Test
    @Tags({@Tag("API"), @Tag("negative")})
    @DisplayName("API negative")
    void jenkkinsTest4() {
        System.out.println("API negative test");

    }
}
