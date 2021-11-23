package ru.netology.web.data;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

@Value
public class LoginPage {
    SelenideElement fieldLogin = $("[data-test-id=login] .input__control");
    SelenideElement fieldPassword = $("[data-test-id=password] .input__control");
    SelenideElement Button = $("[data-test-id=action-login] .button__text");

    SelenideElement successfulLogin = $(".heading_theme_alfa-on-white");
    SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");

    SelenideElement notLogin = $("[data-test-id=login] .input__sub");
    SelenideElement notPassword = $("[data-test-id=password] .input__sub");

    public void setData(UserData userData) {
        getFieldLogin().setValue(userData.getLogin());
        getFieldPassword().setValue(userData.getPassword());
    }

    public void cleaning(SelenideElement field) {
        field.sendKeys(Keys.COMMAND, Keys.CONTROL, "a");
        field.sendKeys(Keys.DELETE);
    }
}
