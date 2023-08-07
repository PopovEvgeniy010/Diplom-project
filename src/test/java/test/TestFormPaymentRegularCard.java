package test;

import com.codeborne.selenide.logevents.SelenideLogger;
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
}

