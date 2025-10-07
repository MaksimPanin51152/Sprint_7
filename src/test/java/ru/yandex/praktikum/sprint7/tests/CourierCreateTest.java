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

public class CourierCreateTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера успешно")
    @Description("Создаем нового курьера и проверяем, что он может войти")
    public void createCourierSuccessfully() {
        Courier courier = new Courier("ninja" + System.currentTimeMillis(), "1234", "Naruto");
        createCourierStep(courier);

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword(), null);
        courierId = loginCourierStep(loginCourier);
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверяем, что создание курьера без логина возвращает ошибку 400")
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "1234", "Naruto");
        createCourierExpectingFailureStep(courier, 400, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Нельзя создать курьера с уже существующим логином")
    @Description("Проверяем, что создание курьера с уже существующим логином возвращает ошибку 409")
    public void cannotCreateDuplicateCourier() {
        String login = "ninja" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", "Naruto");
        createCourierStep(courier);

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword(), null);
        courierId = loginCourierStep(loginCourier);

        createCourierExpectingFailureStep(courier, 409, "Этот логин уже используется. Попробуйте другой.");
    }

    @Step("Создание курьера {courier.login}")
    private void createCourierStep(Courier courier) {
        courierClient.createCourier(courier).then().statusCode(201);
    }

    @Step("Попытка создать курьера {courier.login} и ожидаем ошибку {statusCode} с сообщением '{message}'")
    private void createCourierExpectingFailureStep(Courier courier, int statusCode, String message) {
        courierClient.createCourier(courier)
                .then()
                .statusCode(statusCode)
                .body("message", equalTo(message));
    }

    @Step("Логин курьера {courier.login}")
    private int loginCourierStep(Courier courier) {
        return courierClient.loginCourier(courier)
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Удаление курьера {courierId}")
    private void deleteCourier(int courierId) {
        courierClient.deleteCourier(courierId);
    }
}