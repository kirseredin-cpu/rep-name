package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WsCatalogPage {
    private final ElementsCollection firstCartProduct = $$("div.product-grid div");


    //WELCOME PAGE -> CATALOG PAGE -> PRODUCT PAGE -> CART PAGE

    //CATALOG
    @Step("Выбор первого продукта в каталоге.")
    public WsProductPage selectProduct(int index) {
        firstCartProduct.get(index).click();
        return new WsProductPage();
    }


}
