package ru.yandex.praktikum.sprint7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.sprint7.Courier;
import ru.yandex.praktikum.sprint7.CourierClient;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("ninja" + System.currentTimeMillis(), "1234", "Naruto");
        courierClient.createCourier(courier).then().statusCode(201);
    }

    @After
    public void tearDown() {
        if (courierId == 0) {
            try {
                courierId = courierClient.loginCourier(courier).then().extract().path("id");
            } catch (Exception ignored) {}
        }
        if (courierId != 0) {
            deleteCourierStep(courierId);
        }
    }

    @Test
    @DisplayName("Успешный вход курьера")
    @Description("Проверяем, что курьер может войти с правильными данными")
    public void loginCourierSuccessfully() {
        courierId = loginCourierAndCheckIdStep(courier);
    }

    @Test
    @DisplayName("Попытка входа с неверным паролем")
    @Description("Проверяем, что вход с неверным паролем возвращает ошибку 404")
    public void cannotLoginWithWrongPassword() {
        loginCourierExpectingFailureStep(new Courier(courier.getLogin(), "wrongPassword", null), 404, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Попытка входа с неверным логином")
    @Description("Проверяем, что вход с неверным логином возвращает ошибку 404")
    public void cannotLoginWithWrongLogin() {
        loginCourierExpectingFailureStep(new Courier("wrongLogin", courier.getPassword(), null), 404, "Учетная запись не найдена");
    }

    @Step("Логин курьера {courier.login} и проверка ID")
    private int loginCourierAndCheckIdStep(Courier courier) {
        return courierClient.loginCourier(courier)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Логин курьера {courier.login} и ожидаем ошибку {statusCode} с сообщением '{message}'")
    private void loginCourierExpectingFailureStep(Courier courier, int statusCode, String message) {
        courierClient.loginCourier(courier)
                .then()
                .statusCode(statusCode)
                .body("message", equalTo(message));
    }

    @Step("Удаление курьера {courierId}")
    private void deleteCourierStep(int courierId) {
        courierClient.deleteCourier(courierId);
    }
}
