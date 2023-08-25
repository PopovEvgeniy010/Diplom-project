package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataGenerator;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class FormPage {
    List<SelenideElement> input = $$(".input__control");
    SelenideElement cardNumber = input.get(0);
    SelenideElement month = input.get(1);
    SelenideElement year = input.get(2);
    SelenideElement cardOwner = input.get(3);
    SelenideElement cvcOrCvvNumber = input.get(4);

    public void checkMessageSuccess() {
        $$(".notification__title").find(exactText("Успешно")).should(Condition.appear, Duration.ofSeconds(15));
    }

    public void checkMessageError() {
        $$(".notification__title").find(exactText("Ошибка")).should(Condition.appear, Duration.ofSeconds(15));
    }

    public void checkMessageWrongFormat() {
        $$(".input__sub").find(exactText("Неверный формат")).shouldBe(visible);
    }

    public void checkMessageWrongDate() {
        $$(".input__sub").find(exactText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void checkMessageOverDate() {
        $$(".input__sub").find(exactText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void checkMessageRequiredField() {
        $$(".input__sub").find(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    public void pushСontinueButton() {
        $$(".button__content").find(exactText("Продолжить")).click();
    }


    public void setFormFiled(DataGenerator dataGenerator) {
        cardNumber.setValue(dataGenerator.getNumber());
        month.setValue(dataGenerator.getMonth());
        year.setValue(dataGenerator.getYear());
        cardOwner.setValue(dataGenerator.getHolder());
        cvcOrCvvNumber.setValue(dataGenerator.getCvc());
        pushСontinueButton();
    }
}
