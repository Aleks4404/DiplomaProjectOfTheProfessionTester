package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PagePayment {
    private SelenideElement cardNumberField = $("[placeholder = '0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder = '08']");
    private SelenideElement yearField = $("[placeholder = '22']");
    private SelenideElement ownerField = $$("[class='input__control']").get(3);
    private SelenideElement cvvField = $("[placeholder='999']");
    private SelenideElement buttonForContinue = $(byText("Продолжить"));
    private SelenideElement sendRequestBankButton = $(withText("Отправляем запрос в Банк..."));

    private SelenideElement operationIsApproved = $(withText("Операция одобрена Банком"));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement invalidExpirationDate = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement wrongFormat = $(".input__sub");
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));


    private SelenideElement invalidFormatCard = $$("span.input__sub").get(0);
    private SelenideElement invalidMonth = $$("span.input__sub").get(1);
    private SelenideElement invalidYear = $$("span.input__sub").get(2);
    private SelenideElement invalidOwner = $$("span.input__sub").get(3);
    private SelenideElement invalidCVV = $$("span.input__sub").get(4);


    public void fillingForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvvField.setValue(cardInfo.getCvv());
        buttonForContinue.click();
        sendRequestBankButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void fillFormNoSendRequest(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvvField.setValue(cardInfo.getCvv());
        buttonForContinue.click();
    }

    public void checkOperationIsApproved() {
        operationIsApproved.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkErrorNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkInvalidExpirationDate() {
        invalidExpirationDate.shouldBe(Condition.visible);
    }

    public void checkCardExpired() {
        cardExpired.shouldBe(Condition.visible);
    }

    public void checkWrongFormat() {
        wrongFormat.shouldHave(text("Неверный формат"));
    }

    public void checkRequiredField() {
        requiredField.shouldBe(Condition.visible);
    }

    public void checkInvalidCardNumberT(DataHelper.CardInfo invalidValue) {
        invalidFormatCard.shouldHave(text("Неверный формат"));
    }

    public void checkInvalidMonthT() {
        invalidMonth.shouldHave(text("Неверный формат"));
    }

    public void checkInvalidYearT() {
        invalidYear.shouldHave(text("Неверный формат"));
    }

    public void checkInvalidOwnerT() {
        invalidOwner.shouldHave(text("Неверный формат"));
    }

    public void checkInvalidCVVT() {
        invalidCVV.shouldHave(text("Неверный формат"));
    }
}