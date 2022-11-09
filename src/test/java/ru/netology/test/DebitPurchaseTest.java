package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbUtils;
import ru.netology.page.PaymentMethod;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitPurchaseTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void cleanBase() {
        DbUtils.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Покупка тура с одобренной картой и валидными данными")
    void buyPositiveAllFieldValidApproved() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DbUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Покупка тура с отклоненной картой и валидными данными")
    void buyPositiveAllFieldValidDeclined() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", DbUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Все поля пустые")
    void buyNegativeAllFieldEmpty() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Номер карты из 11 символов")
    void buyNegativeNumberCard11Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getNumberCard11Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Несуществующая карта")
    void buyNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("1 символ в поле 'месяц'")
    void buyNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Месяц действия карты больше 12")
    void buyNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле месяц действия карты значение-00 действующего года")
    void buyNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле месяц действия карты значение-00 следующего года")
    void buyNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Значение-00 в поле год действия")
    void buyNegativeYear00() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле год действия карты 1 символ")
    void buyNegativeYear1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Просроченная карта")
    void buyNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты 6 лет")
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("CVV 1 символ")
    void buyNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("CVV 2 символ")
    void buyNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("в поле владелец карты 1 слово")
    void buyNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты значения написаны кирилицей")
    void buyNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты -(имя + цифры)")
    void buyNegativeOwnerNumeric() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты -(имя + специмволы)")
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }
}
