package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsProductPage {
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement addCartButton = $("input.add-to-cart-button");
    private final SelenideElement successNotification = $("div.bar-notification.success");
    private final SelenideElement headbarItemQuantity = $("span.cart-qty");
    private final SelenideElement cartButton = $("a.ico-cart");
    private final ElementsCollection processorPriceLabel = $$("dl dd ul").get(0).$$("li label");
    private final SelenideElement itemPriceLabel = $("[itemprop=price]");
    private final SelenideElement itemNameLabel = $("[itemprop=name]");



    @Step("Сообщение об успешной отправки товара в корзину")
    public String getProcessorPrice(int index) {
        return processorPriceLabel.get(index).getText();
    }

    //WELCOME PAGE -> CATALOG PAGE -> PRODUCT PAGE -> CART PAGE

    //PRODUCT
    @Step("Ввести количество товара {itemQuantity}")
    public WsProductPage setQuantity(String itemQuantity) {
        quantityInput.setValue(itemQuantity);
        return this;
    }

    @Step("Отправка товара в корзину")
    public WsProductPage submitToCart() {
        addCartButton.click();
        return this;
    }

    @Step("Сообщение об успешной отправки товара в корзину")
    public String getItemName() {
        return itemNameLabel.getText();
    }

    @Step("Сообщение об успешной отправки товара в корзину")
    public String getItemPrice() {
        return itemPriceLabel.getText();
    }


    @Step("Сообщение об успешной отправки товара в корзину")
    public WsProductPage verifyNotificationSuccessMessage() {
        successNotification.shouldBe(visible);
        return this;
    }

    @Step("Отображение количества товара у кнопки 'корзина'.")
    public WsProductPage checkHeadbarCartItemQuantity(String itemQuantity) {
        headbarItemQuantity.shouldHave(text("(" + itemQuantity + ")"));
        return this;
    }

    @Step("Изменение процессора ПК'.")
    public WsProductPage selectProcessor(int index) {
        $$("dl dd ul").get(0).$$("li input").get(index).click();
        return this;
    }

    @Step("Переключение в панель корзины.")
    public WsCartPage enterCartMenu() {
        cartButton.click();
        return new WsCartPage();

}}
