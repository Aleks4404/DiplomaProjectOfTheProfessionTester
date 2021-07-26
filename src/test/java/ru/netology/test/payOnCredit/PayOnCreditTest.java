package ru.netology.test.payOnCredit;

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

public class PayOnCreditTest {
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

    @Test // Тест загрузки вкладки "Купить в кредит"
    void shouldCheckTheDownloadOfThePaymentByCard() {
        pageMain.payCreditByCard();
    }

    @Test //Тест с "APPROVED" картой и валидными данными
    void shouldCheckWithAnApprovedCardAndValidData() {
        val payForm = pageMain.payCreditByCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test // Тест с валидными данными
    void shouldBeCheckedWithValidData() {
        val payForm = pageMain.payCreditByCard();
        val approvedInfo = DataHelper.getApprovedCardInfo();
        payForm.fillingForm(approvedInfo);
        payForm.checkOperationIsApproved();
        String dataSQLPayAmount = SQLHelper.getPaymentAmount();
        assertEquals("45000", dataSQLPayAmount);
    }

    @Test // Тест "DECLINED" карты с валидными данными
    void shouldCheckTheDeclinedCardAndTheValidData() {
        val payForm = pageMain.payCreditByCard();
        val declinedInfo = DataHelper.getDeclinedCardInfo();
        payForm.fillingForm(declinedInfo);
        payForm.checkErrorNotification();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test // Тест невалидной карты
    void shouldheckTheInvalidCard() {
        val payForm = pageMain.payCreditByCard();
        val invalidCardNumber = DataHelper.getInvalidCardNumberInfo();
        payForm.fillingForm(invalidCardNumber);
        payForm.checkErrorNotification();
    }

    @Test // Тест невалидного месяца
    void shouldCheckTheInvalidMonth() {
        val payForm = pageMain.payCreditByCard();
        val invalidMonth = DataHelper.getInvalidMonthInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест невалидного месяца
    void shouldCheckTheInvalidMonthZero() {
        val payForm = pageMain.payCreditByCard();
        val invalidMonth = DataHelper.getInvalidMonthZeroInfo();
        payForm.fillFormNoSendRequest(invalidMonth);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест с истекшим сроком действия карты
    void shouldBeCheckedWithAnExpiredExpirationDate() {
        val payForm = pageMain.payCreditByCard();
        val expiredYear = DataHelper.getExpiredYearInfo();
        payForm.fillFormNoSendRequest(expiredYear);
        payForm.checkCardExpired();
    }

    @Test // Тест с неверно указаным сроком действия карты
    void shouldCheckWithTheIncorrectlySpecifiedCardExpirationDate() {
        val payForm = pageMain.payCreditByCard();
        val invalidYear = DataHelper.getInvalidYearInfo();
        payForm.fillFormNoSendRequest(invalidYear);
        payForm.checkInvalidExpirationDate();
    }

    @Test // Тест данные владельца карты на киррилице
    void shouldCheckTheOwnersDataInCyrillic() {
        val payForm = pageMain.payCreditByCard();
        val invalidOwner = DataHelper.getInvalidOwnerInfo();
        payForm.fillFormNoSendRequest(invalidOwner);
        payForm.checkWrongFormat();
    }

    @Test // Тест отправка пустой формы
    void shouldSendAnEmptyForm() {
        val payForm = pageMain.payCreditByCard();
        val emptyFields = DataHelper.getEmptyFields();
        payForm.fillFormNoSendRequest(emptyFields);
        payForm.checkWrongFormat();
        payForm.checkRequiredField();
    }

    @Test // Тест отправить сперва пустую форму заявки, затем заполнить валидными данными и отправить повторно
    void shouldSendTheFormEmptyAndThenWithTheOwnersData() {
        val payForm = pageMain.payCreditByCard();
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
        val payForm = pageMain.payCreditByCard();
        val invalidValue = DataHelper.getInvalidCardForm();
        payForm.fillFormNoSendRequest(invalidValue);
        payForm.checkInvalidMonthT();
        payForm.checkInvalidYearT();
        payForm.checkInvalidOwnerT();
        payForm.checkInvalidCVVT();
    }
}
