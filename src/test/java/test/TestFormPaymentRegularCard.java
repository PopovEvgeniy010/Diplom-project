package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.model.Status;
import io.qameta.allure.selenide.AllureSelenide;
import data.SQl;
import page.FormPage;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

public class
TestFormPaymentRegularCard {
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
    @DisplayName("PaymentWithRegularActiveCard")
    void shouldPayByApprovedCard() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayByDeclinedCard() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayByUnknownCard() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayInvalidCardNumberField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayInvalidMonthField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayInvalidYearField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayInvalidCardOwnerField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayInvalidCVVField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayEmptyCardNumberField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayEmptyMonthField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayEmptyYearField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayEmptyCardOwnerField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldNoPayEmptyCVVField() throws SQLException {
        formPage.buyForYourMoney();
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
    void shouldPayByApprovedCardStatusInDB() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SQl.checkPaymentStatus(Status.PASSED);
    }

    @Test
    @DisplayName("PaymentOnAnInactiveCardDatabaseRecordCheck")
    void shouldNoPayByDeclinedCardStatusInDB() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("08");
        formPage.setCardYear("23");
        formPage.setCardOwner("Ivan Popov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        SQl.checkPaymentStatus(Status.FAILED);
    }
}

