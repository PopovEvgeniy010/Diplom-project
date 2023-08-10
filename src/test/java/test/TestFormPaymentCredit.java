package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.model.Status;
import io.qameta.allure.selenide.AllureSelenide;
import data.SQl;
import page.FormPage;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

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

    @AfterEach
    void clearAll() throws SQLException {
        SQl.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("PaymentByActiveCreditCard")
    void shouldPayByApprovedCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("PaymentForAnInactiveCard")
    void shouldNoPayByDeclinedCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByUnknownCard")
    void shouldNoPayByUnknownCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidCardNumber")
    void shouldNoPayInvalidCardNumberFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("3333 2323 DSDF ASSD");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidMonthNumber")
    void shouldNoPayInvalidMonthFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("13");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidYearNumber")
    void shouldNoPayInvalidYearFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("18");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidOwnerField")
    void shouldNoPayInvalidCardOwnerFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Bdfy 1213 Попов 12");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidCVVField")
    void shouldNoPayInvalidCVVFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("12D");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyCardNumber")
    void shouldNoPayEmptyCardNumberFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyMonthNumber")
    void shouldNoPayEmptyMonthFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyYearNumber")
    void shouldNoPayEmptyYearFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyFieldOwner")
    void shouldNoPayEmptyCardOwnerFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyCVVField")
    void shouldNoPayEmptyCVVFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByActiveCardDatabaseRecordCheck")
    void shouldPayByApprovedCardInCreditStatusInDB() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SQl.checkCreditStatus(Status.PASSED);
    }

    @Test
    @DisplayName("PaymentOnAnInactiveCardDatabaseRecordCheck")
    void shouldPayByDeclinedCardInCreditStatusInDB() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SQl.checkCreditStatus(Status.FAILED);
    }
}
