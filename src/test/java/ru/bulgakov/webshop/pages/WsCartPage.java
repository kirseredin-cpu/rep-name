package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WsCartPage {
    // TO DO PROCESSOR private final ElementsCollection headbarMenuButtons  =  $$("ul.top-menu li a");
    private final SelenideElement cartItemName  = $("a.product-name");
    private final SelenideElement cartItemQuantity  = $("input.qty-input");
    private final SelenideElement cartSubtotal  = $("span.product-subtotal");



    //WELCOME PAGE -> CATALOG PAGE -> PRODUCT PAGE -> CART PAGE

    //CARTPAGE


    @Step("Получение количество товара")
    public String getCartQuantity () {
        return cartItemQuantity.getAttribute("value");
    }
    @Step("Получение именования товара")
    public String getItemName() {
        return cartItemName.getText();
    }
    @Step("Получение итоговой суммы корзины")
    public Float getSubtotal () {
        return Float.parseFloat(cartSubtotal.getText());
    }

    @Step("Подтверждение цены товара. GetSubtotal and Calculate expectedTotal")
    public String calculateExpectedSubtotal(String itemPrice, String itemQuantity, String processorPrice) {

   /*     float processorValue = 0f;
        Pattern pattern = Pattern.compile("[-+]?\\d*\\.?\\d+");
        Matcher matcher = pattern.matcher(processorPrice);

        if (matcher.find()) {
            processorValue = Float.parseFloat(matcher.group());
            System.out.println(processorValue); // 15.0
        } else {
            System.out.println("slow processor"); // 15.0
        }*/
        //matcher example
        float price = Float.parseFloat(itemPrice);
        float quantity = Float.parseFloat(itemQuantity);
        String  expectedTotal = String.valueOf(price * quantity) ;


        return expectedTotal;
    }


}
