package ru.bulgakov.webshop;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.bulgakov.webshop.config.WebDriverConfig;
import ru.bulgakov.webshop.util.AttachManager;


import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.getSelenoidChromeOptions;
import static ru.bulgakov.webshop.config.Config.getWebDriverConfig;

public class TestBase {

    private static final WebDriverConfig config = getWebDriverConfig();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());


        Configuration.browserSize = config.browserSize();
        Configuration.browser = config.browser();


        if ("remote".equals(System.getProperty("run"))) {
            Configuration.remote =
                    "https://" + config.selenoidUser() + ":" + config.selenoidPassword() +
                            "@" + config.selenoidUrl();
            Configuration.browserCapabilities = getSelenoidChromeOptions();
        }


    }


    @AfterEach
    void after() {
        clearBrowserLocalStorage();
        clearBrowserCookies();

        AttachManager.takeScreenshot();
        AttachManager.getPageSource();
        AttachManager.getBrowserConsoleLogs();

        if ("remote".equals(config.run()))
        AttachManager.addVideo();
    }

 /*   @BeforeEach
    void closeDriver() {
        closeWebDriver();
    }*/

}
