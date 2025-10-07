package ru.yandex.praktikum.sprint7;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.sprint7.BaseClient;

public class OrderClient extends BaseClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(Object order) {
        return post(ORDER_PATH, order);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return get(ORDER_PATH);
    }

    @Step("Отмена заказа с track = {track}")
    public Response cancelOrder(int track) {
        String body = "{ \"track\": " + track + " }";
        return put(ORDER_PATH + "/cancel", body);
    }
}


