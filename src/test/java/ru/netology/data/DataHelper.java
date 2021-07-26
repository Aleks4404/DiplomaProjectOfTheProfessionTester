package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "0000 0000 0000  0001";
    }

    public static String getEmptyCardNumbervalue() {
        return " ";
    }

    public static String getvalidMonth() {
        return "08";
    }

    public static String getInvalidMonth() {
        return "99";
    }

    public static String getInvalidMonthZero() {
        return "00";
    }

    public static String getEmptyMonthvalue() {
        return " ";
    }

    public static String getvalidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(1);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getExpiredYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.minusYears(11);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getInvalidYear() {
        LocalDate year = LocalDate.now();
        LocalDate newYear = year.plusYears(78);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        return newYear.format(yearFormatter);
    }

    public static String getEmptyYearvalue() {
        return " ";
    }

    public static String getvalidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String getInvalidOwner() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getEmptyOwnervalue() {
        return " ";
    }

    public static String getvalidCVV() {
        return "123";
    }

    public static String getInvalidCVV() {
        return "12";
    }

    public static String getEmptyCVVvalue() {
        return " ";
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(getApprovedCardNumber(), getvalidMonth(), getvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(getDeclinedCardNumber(), getvalidMonth(), getvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getInvalidCardNumberInfo() {
        return new CardInfo(getInvalidCardNumber(), getvalidMonth(), getvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getInvalidMonthInfo() {
        return new CardInfo(getApprovedCardNumber(), getInvalidMonth(), getvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getInvalidMonthZeroInfo() {
        return new CardInfo(getApprovedCardNumber(), getInvalidMonthZero(), getvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getExpiredYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getvalidMonth(), getExpiredYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getInvalidYearInfo() {
        return new CardInfo(getApprovedCardNumber(), getvalidMonth(), getInvalidYear(), getvalidOwner(), getvalidCVV());
    }

    public static CardInfo getInvalidOwnerInfo() {
        return new CardInfo(getApprovedCardNumber(), getvalidMonth(), getvalidYear(), getInvalidOwner(), getvalidCVV());
    }

    public static CardInfo getEmptyFields() {
        return new CardInfo(getEmptyCardNumbervalue(), getEmptyMonthvalue(), getEmptyYearvalue(),
                getEmptyOwnervalue(), getEmptyCVVvalue());
    }

    public static CardInfo getInvalidvaluesOfForm() {
        return new CardInfo(getInvalidCardNumber(), getInvalidMonth(), getInvalidYear(),
                getInvalidOwner(), getInvalidCVV());
    }

    public static CardInfo getInvalidCardForm() {
        return new CardInfo(getInvalidCardNumber(), getInvalidMonth(), getInvalidYear(), getInvalidOwner(), getInvalidCVV());
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvv;
    }
}