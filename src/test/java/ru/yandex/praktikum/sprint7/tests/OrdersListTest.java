package ru.yandex.praktikum.sprint7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.sprint7.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Получение списка всех заказов")
    public void getOrdersListSuccessfully() {
        getOrdersListStep();
    }

    @Step("Получение списка заказов и проверка ответа")
    private void getOrdersListStep() {
        orderClient.getOrders()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}