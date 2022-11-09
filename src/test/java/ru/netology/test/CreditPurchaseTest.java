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

public class CreditPurchaseTest {

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
    @DisplayName("Покупка тура в кредит с одобренной картой и валидными данными")
    void creditPositiveAllFieldValidApproved() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", DbUtils.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Покупка тура в кредит с отклоненной картой и валидными данными")
    void creditPositiveAllFieldValidDeclined() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", DbUtils.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Все поля пустые")
    void creditNegativeAllFieldEmpty() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Номер карты из 11 символов")
    void creditNegativeNumberCard11Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberCard11Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Несуществующая карта")
    void creditNegativeCardNotInDatabase() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("1 символ в поле 'месяц'")
    void creditNegativeMonth1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Месяц действия карты больше 12")
    void creditNegativeMonthOver12() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле месяц действия карты значение-00 действующего года")
    void creditNegativeMonth00ThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле месяц действия карты значение-00 следующего года")
    void creditNegativeMonth00OverThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Значение-00 в поле год действия")
    void creditNegativeYear00() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле год действия карты 1 символ")
    void creditNegativeYear1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Просроченная карта")
    void creditNegativeYearUnderThisYear() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("Срок действия карты 6 лет")
    void creditNegativeYearOverThisYearOn6() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("CVV 1 символ")
    void creditNegativeCvv1Symbol() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("CVV 2 символ")
    void creditNegativeCvv2Symbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("в поле владелец карты 1 слово")
    void creditNegativeOwner1Word() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты значения написаны кирилицей")
    void creditNegativeOwnerCirillic() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolderCirillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты -(имя + цифры)")
    void creditNegativeOwnerNumeric() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }

    @Test
    @DisplayName("В поле владелец карты -(имя + специмволы)")
    void creditNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentMethod();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", DbUtils.getOrderCount());
    }
}
