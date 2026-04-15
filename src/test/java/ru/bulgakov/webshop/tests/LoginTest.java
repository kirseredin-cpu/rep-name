package ru.bulgakov.webshop.tests;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.WsLoginPage;
import ru.bulgakov.webshop.pages.WsWelcomePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static ru.bulgakov.webshop.config.Config.*;

public class LoginTest extends TestBase {
    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @Nested
    public class PositiveTests {
        @BeforeEach
        void before(){
            password = faker.harryPotter().character() + faker.number().positive();
            email = faker.internet().emailAddress();

            open(WEB_SHOP_REGISTRATION_URL, ru.bulgakov.webshop.pages.WsRegistrationPage.class)
                    .register(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            email,
                            password)
                    .checkUserLoggedIn(email);

            clearBrowserLocalStorage();
            clearBrowserCookies();
        }

        @Test
        @Severity(CRITICAL)
        @DisplayName("Успешная логинизация нового пользователя")
        @Owner("Kirill S.")
        @Tags({@Tag("UI"), @Tag("positive")})
        @Link("TASK-35")
        void succesLoginTest(){

            open(WEB_SHOP_URL, WsWelcomePage.class)
                    .entryLoginPanel()
                    .verifySignIn()
                    .enterEmail(email)
                    .enterPassword(password)
                    .rememberMeCheck()
                    .submitLogin()
                    .checkUserLoggedIn(email);
        }
    }

    @ParameterizedTest(name = "Авторизация с невалидным email: {0}")
    @Severity(NORMAL)
    @Owner("Kirill S.")
    @Link("TASK-220")
    @Tags({@Tag("UI"), @Tag("negative")})
    @CsvFileSource(resources = "/emails.csv")
    void invalidEmailLoginTest(String email) {
        open(WEB_SHOP_LOGIN_URL, WsLoginPage.class)
                .enterEmail(email)
                .enterPassword("password")
                .verifyValidatiionErrorMessage()
                .submitLogin();

    }
}
