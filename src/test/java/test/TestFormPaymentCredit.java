package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import data.SqlHelper;
import lombok.SneakyThrows;
import page.FormPage;
import org.junit.jupiter.api.*;
import page.MainPage;

import static data.DataGenerator.*;
import static data.SqlHelper.getCheckCreditStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestFormPaymentCredit {
    private FormPage formPage;
    private MainPage mainPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
        mainPage = new MainPage();
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


    @Test
    @DisplayName("PaymentByActiveCreditCard")
    void shouldPayByApprovedCardInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(generatedApprovedCard("en"));
        formPage.checkMessageSuccess();

    }

    @Test
    @DisplayName("PaymentForAnInactiveCard")
    void shouldNoPayByDeclinedCardInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(generatedDeclinedCard("en"));
        formPage.checkMessageError();
    }


    @Test
    @DisplayName("PaymentByUnknownCard")
    void shouldNoPayByUnknownCardInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(generatedNoDbCard());
        formPage.checkMessageError();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidCardNumber")
    void shouldNoPayInvalidCardNumberFieldInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(new DataGenerator("3333 2323 DSDF ASSD", "09", "23", "LOMAKIN MIKHAIL", "186"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidMonthNumber")
    void shouldNoPayInvalidMonthFieldInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "13", "25", "PETROV BORIS", "762"));
        formPage.checkMessageWrongDate();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidYearNumber")
    void shouldNoPayInvalidYearFieldInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "11", "19", "OLGA MATVEEVA", "999"));
        formPage.checkMessageOverDate();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidOwnerField")
    void shouldNoPayInvalidCardOwnerFieldInCredit() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "02", "27", "Bdfy 1213 Попов 12", "888"));
        formPage.checkMessageError();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidCVVField")
    void shouldNoPayInvalidCVVFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "06", "24", "MAMAEVA ZINA", "35K"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyCardNumber")
    void shouldNoPayEmptyCardNumberFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("", "08", "26", "ELIZAVETA MOROZOVA", "728"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyMonthNumber")
    void shouldNoPayEmptyMonthFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "", "27", "OLEG POPOV", "243"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyYearNumber")
    void shouldNoPayEmptyYearFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "01", "", "BOGOMAZOVA MARIIA", "798"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyFieldOwner")
    void shouldNoPayEmptyCardOwnerFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "10", "25", "", "789"));
        formPage.checkMessageRequiredField();
    }

    @Test
    @DisplayName("PaymentByCardWithAnEmptyCVVField")
    void shouldNoPayEmptyCVVFieldInCredit() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "08", "26", "SIDOROV PETR", ""));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByActiveCardDatabaseRecordCheck")
    void shouldPayByApprovedCardInCreditStatusInDB() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(generatedApprovedCard("en"));
        formPage.checkMessageSuccess();
        var expectedStatus = "APPROVED";
        var actualStatus = getCheckCreditStatus();
        assertEquals(expectedStatus, actualStatus);
    }


    @Test
    @DisplayName("PaymentOnAnInactiveCardDatabaseRecordCheck")
    void shouldPayByDeclinedCardInCreditStatusInDB() {
        mainPage.buyOnCredit();
        formPage.setFormFiled(generatedDeclinedCard("en"));
        formPage.checkMessageSuccess();
        var expectedStatus = "DECLINED";
        var actualStatus = getCheckCreditStatus();
        assertEquals(expectedStatus, actualStatus);
    }
}
