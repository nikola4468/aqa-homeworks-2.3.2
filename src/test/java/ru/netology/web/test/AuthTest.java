package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    String active = "active";
    String blocked = "blocked";

    public LoginPage loginPage = new LoginPage();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var userData = DataGenerator.sendRequest(active);
        loginPage.setData(userData);
        loginPage.getButton().click();
        loginPage.getSuccessfulLogin().shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        loginPage.getFieldLogin().setValue(DataGenerator.badUser(active).getLogin());
        loginPage.getFieldPassword().setValue(DataGenerator.badUser(active).getPassword());
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        var userData = DataGenerator.sendRequest(blocked);
        loginPage.setData(userData);
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        var userData = DataGenerator.sendRequest(active);
        loginPage.setData(userData);
        loginPage.cleaning(loginPage.getFieldLogin());
        loginPage.getFieldLogin().setValue(DataGenerator.badUser(active).getLogin());
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        var userData = DataGenerator.sendRequest(active);
        loginPage.setData(userData);
        loginPage.cleaning(loginPage.getFieldPassword());
        loginPage.getFieldPassword().setValue(DataGenerator.badUser(active).getPassword());
        loginPage.getButton().click();
        loginPage.getErrorNotification().shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfNotLogin() {
        var userData = DataGenerator.sendRequest(active);
        loginPage.setData(userData);
        loginPage.cleaning(loginPage.getFieldLogin());
        loginPage.getButton().click();
        loginPage.getNotLogin().shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldGetErrorIfNotPassword() {
        var userData = DataGenerator.sendRequest(active);
        loginPage.setData(userData);
        loginPage.cleaning(loginPage.getFieldPassword());
        loginPage.getButton().click();
        loginPage.getNotPassword().shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldGetErrorIfNotLoginAndPassword() {
        loginPage.getButton().click();
        loginPage.getNotLogin().shouldHave(text("Поле обязательно для заполнения"));
    }
}