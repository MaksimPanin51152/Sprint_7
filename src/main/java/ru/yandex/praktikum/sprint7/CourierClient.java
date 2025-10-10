package ru.yandex.praktikum.sprint7;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierClient extends BaseClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создать курьера: {courier.login}")
    public Response createCourier(Courier courier) {
        return post(COURIER_PATH, courier);
    }

    @Step("Логин курьера: {courier.login}")
    public Response loginCourier(Courier courier) {
        return post(COURIER_PATH + "/login", courier);
    }

    @Step("Удалить курьера с id = {courierId}")
    public Response deleteCourier(int courierId) {
        return delete(COURIER_PATH + "/" + courierId);
    }
}