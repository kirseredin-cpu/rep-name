package ru.bulgakov.webshop.tests;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.WsCartPage;
import ru.bulgakov.webshop.pages.WsProductPage;
import ru.bulgakov.webshop.pages.WsWelcomePage;
import ru.bulgakov.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class CartTest extends TestBase {
    private static final Faker faker = new Faker();
    private final AuthSteps authSteps = new AuthSteps();
    private String itemQuantity = "2";
    private Integer index = 1;
    private Integer productIndex = 0;

    private float getProcessorPrice(int processorIndex) {
        return switch (processorIndex) {
            case 0 -> 0f;
            case 1 -> 15f;
            case 2 -> 100f;
            default -> throw new IllegalArgumentException(
                    "Unknown processor index: " + processorIndex);
        };
    }

    @BeforeEach
    void beforeEach() {
        authSteps.registerNewUser();
    }

    @Test
    @Tags({@Tag("UI"), @Tag("positive")})
    @DisplayName("Успешное добавление товара в корзину")
    @Owner("Kirill S.")
    @Severity(BLOCKER)
    @Link("TASK-221")
    void addItemToCartSuccessfully() {

        String property = System.getProperty("run", "local");
        System.out.println(property);

        WsProductPage productPage = new WsProductPage();
        WsCartPage cartPage = new WsCartPage();

        open(WEB_SHOP_URL, WsWelcomePage.class)
                .hoverComputerMenu()
                .clickComputerButton()
                .selectProduct(productIndex)
                .selectProcessor(index)
                .setQuantity(itemQuantity);
        String itemName = productPage.getItemName();
        String itemPrice = productPage.getItemPrice();

        productPage.submitToCart()
                .verifyNotificationSuccessMessage()
                .checkHeadbarCartItemQuantity(itemQuantity)
                .enterCartMenu();

        float processorPrice = getProcessorPrice(index);
        Float expectedTotal = (Float.parseFloat(itemPrice) + processorPrice) * Float.parseFloat(itemQuantity);
        assertAll(
                () -> assertEquals(itemName, cartPage.getItemName()),
                () -> assertEquals(expectedTotal, cartPage.getSubtotal()),
                () -> assertEquals(itemQuantity, cartPage.getCartQuantity())
        );


    }
}
