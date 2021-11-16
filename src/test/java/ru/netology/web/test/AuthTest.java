package ru.netology.web.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.LoginPage;
import ru.netology.web.data.UserData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    String wrongLogin = "wrongLogin";
    String wrongPassword = "wrongPassword";

    String active = "active";
    String blocked = "blocked";

    public LoginPage loginPage = new LoginPage();

    public void setData(UserData userData) {
        loginPage.getFieldLogin().setValue(userData.getLogin());
        loginPage.getFieldPassword().setValue(userData.getPassword());
    }

    public void cleaning(SelenideElement field) {
        field.sendKeys(Keys.COMMAND, Keys.CONTROL, "a");
        field.sendKeys(Keys.DELETE);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var userData = DataGenerator.setUpAll(active);
        setData(userData);
        loginPage.getButton().click();
        loginPage.getSuccessfulLogin().shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        loginPage.getFieldLogin().setValue("NotRegisteredUser");
        loginPage.getFieldPassword().setValue("Password");
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        var userData = DataGenerator.setUpAll(blocked);
        setData(userData);
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        var userData = DataGenerator.setUpAll(active);
        setData(userData);
        cleaning(loginPage.getFieldLogin());
        loginPage.getFieldLogin().setValue(wrongLogin);
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        var userData = DataGenerator.setUpAll(active);
        setData(userData);
        cleaning(loginPage.getFieldPassword());
        loginPage.getFieldPassword().setValue(wrongPassword);
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfNotLogin() {
        var userData = DataGenerator.setUpAll(active);
        setData(userData);
        cleaning(loginPage.getFieldLogin());
        loginPage.getButton().click();
        loginPage.getNotLogin().shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldGetErrorIfNotPassword() {
        var userData = DataGenerator.setUpAll(active);
        setData(userData);
        cleaning(loginPage.getFieldPassword());
        loginPage.getButton().click();
        loginPage.getNotPassword().shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldGetErrorIfNotLoginAndPassword() {
        loginPage.getButton().click();
        loginPage.getNotLogin().shouldHave(text("Поле обязательно для заполнения"));
    }
}