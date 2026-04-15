package ru.bulgakov.webshop.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class JenkinsTest {
    @Test
    @Tags({@Tag("UI"), @Tag("positive")})
    void jenkkinsTest1() {
        System.out.println("Ui positive test");
    }
    @Test
    @Tags({@Tag("UI"), @Tag("negative")})
    void jenkkinsTest2() {
        System.out.println("Ui negative test");

    }
    @Test
    @Tags({@Tag("API"), @Tag("positive")})
    void jenkkinsTest3() {
        System.out.println("API positive test");

    }
    @Test
    @Tags({@Tag("API"), @Tag("negative")})
    void jenkkinsTest4() {
        System.out.println("API negative test");

    }
}
