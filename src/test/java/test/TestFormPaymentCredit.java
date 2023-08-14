package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.model.Status;
import io.qameta.allure.selenide.AllureSelenide;
import data.SqlHelper;
import lombok.SneakyThrows;
import page.FormPage;
import page.MainPage;
import org.junit.jupiter.api.*;


public class TestFormPaymentCredit {
    private FormPage formPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @SneakyThrows
    @AfterEach
    void clearAll() {
        SqlHelper.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByActiveCreditCard")
    void shouldPayByApprovedCardInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }


    @SneakyThrows
    @Test
    @DisplayName("PaymentForAnInactiveCard")
    void shouldNoPayByDeclinedCardInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByUnknownCard")
    void shouldNoPayByUnknownCardInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithInvalidCardNumber")
    void shouldNoPayInvalidCardNumberFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("3333 2323 DSDF ASSD");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithInvalidMonthNumber")
    void shouldNoPayInvalidMonthFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("13");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithInvalidYearNumber")
    void shouldNoPayInvalidYearFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("18");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithInvalidOwnerField")
    void shouldNoPayInvalidCardOwnerFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Bdfy 1213 Попов 12");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithInvalidCVVField")
    void shouldNoPayInvalidCVVFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("12D");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithAnEmptyCardNumber")
    void shouldNoPayEmptyCardNumberFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithAnEmptyMonthNumber")
    void shouldNoPayEmptyMonthFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithAnEmptyYearNumber")
    void shouldNoPayEmptyYearFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithAnEmptyFieldOwner")
    void shouldNoPayEmptyCardOwnerFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByCardWithAnEmptyCVVField")
    void shouldNoPayEmptyCVVFieldInCredit() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentByActiveCardDatabaseRecordCheck")
    void shouldPayByApprovedCardInCreditStatusInDB() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SqlHelper.checkCreditStatus(Status.PASSED);
    }

    @SneakyThrows
    @Test
    @DisplayName("PaymentOnAnInactiveCardDatabaseRecordCheck")
    void shouldPayByDeclinedCardInCreditStatusInDB() {
        MainPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SqlHelper.checkCreditStatus(Status.FAILED);
    }
}
