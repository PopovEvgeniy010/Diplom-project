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
import static data.SqlHelper.getcheckPaymentStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestFormPaymentRegularCard {
    private FormPage formPage;
    private MainPage mainPage;

    @BeforeEach
    void setUpFormPage() {
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
    @DisplayName("PaymentWithRegularActiveCard")
    void shouldPayByApprovedCard() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(generatedApprovedCard("en"));
        formPage.checkMessageSuccess();
    }


    @Test
    @DisplayName("PaymentForAnInactiveCard")
    void shouldNoPayByDeclinedCard() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(generatedDeclinedCard("en"));
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByUnknownCard")
    void shouldNoPayByNoDbCard() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(generatedNoDbCard());
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidCardNumber")
    void shouldNoPayInvalidCardNumberField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("3333 2323 DSDF ASSD", "12", "23", "LOMAKIN MIKHAIL", "899"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidMonthNumber")
    void shouldNoPayInvalidMonthField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "13", "24", "SIDOROV PETR", "745"));
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidYearNumber")
    void shouldValidateYearField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "10", "19", "BOGOMAZOVA MARIIA", "982"));
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("PaymentByCardWithInvalidOwnerField")
    void shouldNoPayInvalidCardOwnerField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "03", "25", "Bdfy 1213 Попов 12", "654"));
        formPage.checkMessageError();
    }


    @Test
    @DisplayName("PaymentByCardWithInvalidCVVField")
    void shouldNoPayInvalidCVVField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "07", "27", "OLEG POPOV", "12D"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyCardNumber")
    void shouldNoPayEmptyCardNumberField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("", "09", "23", "ELIZAVETA MOROZOVA", "140"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyMonthNumber")
    void shouldNoPayEmptyMonthField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "", "25", "MAMAEVA ZINA", "875"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyYearNumber")
    void shouldNoPayEmptyYearField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "08", "", "OLGA MATVEEVA", "149"));
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyFieldOwner")
    void shouldNoPayEmptyCardOwnerField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "04", "23", "", "564"));
        formPage.checkMessageRequiredField();
    }


    @Test
    @DisplayName("PaymentByCardWithAnEmptyCVVField")
    void shouldNoPayEmptyCVVField() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(new DataGenerator("4444 4444 4444 4441", "11", "26", "PETROV BORIS", ""));
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("PaymentByActiveCardDatabaseRecordCheck")
    void shouldPayByApprovedCardStatusInDB() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(generatedApprovedCard("en"));
        formPage.checkMessageSuccess();
        var expectedStatus = "APPROVED";
        var actualStatus = getcheckPaymentStatus();
        assertEquals(expectedStatus, actualStatus);
    }


    @Test
    @DisplayName("PaymentOnAnInactiveCardDatabaseRecordCheck")
    void shouldNoPayByDeclinedCardStatusInDB() {
        mainPage.buyForYourMoney();
        formPage.setFormFiled(generatedDeclinedCard("en"));
        formPage.checkMessageSuccess();
        var expectedStatus = "DECLINED";
        var actualStatus = getcheckPaymentStatus();
        assertEquals(expectedStatus, actualStatus);
    }
}