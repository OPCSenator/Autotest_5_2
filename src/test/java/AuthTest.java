import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;


class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $x("//input[@name = 'login']").setValue(registeredUser.getLogin());
        $x("//input[@name = 'password']").setValue(registeredUser.getPassword());
        $x("//span[@class = 'button__text']").click();
        $("[id=\"root\"]").shouldHave(Condition.text("  Личный кабинет")).click();
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        Configuration.holdBrowserOpen = true;
        $x("//input[@name = 'login']").setValue(notRegisteredUser.getLogin());
        $x("//input[@name = 'password']").setValue(notRegisteredUser.getPassword());
        $x("//span[@class = 'button__text']").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(Condition.visible, Duration.ofMillis(5000)).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $x("//input[@name = 'login']").setValue(blockedUser.getLogin());
        $x("//input[@name = 'password']").setValue(blockedUser.getPassword());
        $x("//span[@class = 'button__text']").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(Condition.visible, Duration.ofMillis(5000)).shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));

    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $x("//input[@name = 'login']").setValue(wrongLogin);
        $x("//input[@name = 'password']").setValue(registeredUser.getPassword());
        $x("//span[@class = 'button__text']").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(Condition.visible, Duration.ofMillis(5000)).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        Configuration.holdBrowserOpen = true;
        $x("//input[@name = 'login']").setValue(registeredUser.getLogin());
        $x("//input[@name = 'password']").setValue(wrongPassword);
        $x("//span[@class = 'button__text']").click();
        $x("//*[@data-test-id=\"error-notification\"]").shouldBe(Condition.visible, Duration.ofMillis(5000)).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }
}