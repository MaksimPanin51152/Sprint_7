package ru.yandex.praktikum.sprint7;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderClient extends BaseClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return post(ORDER_PATH, order);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return get(ORDER_PATH);
    }

    @Step("Принятие заказа с id = {orderId}")
    public Response acceptOrder(int orderId, int courierId) {
        return put(ORDER_PATH + "accept/" + orderId + "?courierId=" + courierId, null);
    }
}