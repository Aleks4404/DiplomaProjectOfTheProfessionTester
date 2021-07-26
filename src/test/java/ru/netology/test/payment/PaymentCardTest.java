package ru.netology.test.payment;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PageMain;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentCardTest{
    PageMain pageMain = new PageMain();

    @BeforeEach
    void openForTests() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test // Тест загрузки вкладки "Купить"
    void shouldCheckTheDownloadOfThePaymentByCard() {
        pageMain.payByDebitCard();
    }

    @Test //Тест с "APPROVED" картой и валидными данными
    void shouldCheckWithAnApprovedCardAndValidData() {
        val payForm = pageMain.payByDebitCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test // Тест с валидными данными
    void shouldBeCheckedWithValidData() {
        val payForm = pageMain.payByDebitCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayAmount = SQLHelper.getPaymentAmount();
        assertEquals("45000", dataSQLPayAmount);
    }

    @Test // Тест "DECLINED" карты с валидными данными
    void shouldCheckTheDeclinedCardAndTheValidData() {
        val payForm = pageMain.payByDebitCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.checkErrorNotification();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test // Тест невалидной карты
    void shouldheckTheInvalidCard() {
        val payForm = pageMain.payByDebitCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.checkErrorNotification();
    }

    @Test // Тест невалидного месяца
    void shouldCheckTheInvalidMonth() {
        val payForm = pageMain.payByDebitCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест невалидного месяца
    void shouldCheckTheInvalidMonthZero() {
        val payForm = pageMain.payByDebitCard();
        val invalidMonth = DataHelper.getInvalidMonthZeroInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест с истекшим сроком действия карты
    void shouldBeCheckedWithAnExpiredExpirationDate() {
        val payForm = pageMain.payByDebitCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.checkCardExpired();
    }

    @Test // Тест с неверно указаным сроком действия карты
    void shouldCheckWithTheIncorrectlySpecifiedCardExpirationDate() {
        val payForm = pageMain.payByDebitCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест данные владельца карты на киррилице
    void shouldCheckTheOwnersDataInCyrillic() {
        val payForm = pageMain.payByDebitCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.checkWrongFormat();
    }

    @Test // Тест отправка пустой формы
    void shouldSendAnEmptyForm() {
        val payForm = pageMain.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
    }

    @Test // Тест отправить сперва пустую форму заявки, затем заполнить валидными данными и отправить повторно
    void shouldSendTheFormEmptyAndThenWithTheOwnersData() {
        val payForm = pageMain.payByDebitCard();
        val emptyFields = DataHelper.getEmptyFields();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
    }

    @Test // Тест с невалидными данными всех полей
    void shouldBeCheckedWithInvalidDataOfAllFields() {
        val payForm = pageMain.payByDebitCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payForm.fillFormNoSendRequest(invalidValue);
        payForm.checkInvalidMonthT();
        payForm.checkInvalidYearT();
        payForm.checkInvalidOwnerT();
        payForm.checkInvalidCVVT();
    }
}