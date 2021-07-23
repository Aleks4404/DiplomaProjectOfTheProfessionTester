package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class PageMain {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private SelenideElement paymentByCreditCard = $(byText("Кредит по данным карты"));

    public PagePayment payByDebitCard() {
        buyButton.click();
        paymentByCard.shouldHave(Condition.visible);
        return new PagePayment();
    }

    public PagePayment payCreditByCard() {
        creditButton.click();
        paymentByCreditCard.shouldHave(Condition.visible);
        return new PagePayment();
    }
}
