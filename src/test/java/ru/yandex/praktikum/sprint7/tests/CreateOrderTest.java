package ru.yandex.praktikum.sprint7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.sprint7.Order;
import ru.yandex.praktikum.sprint7.OrderClient;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Создание заказа с одним цветом")
    @Description("Создаем заказ с одним цветом и проверяем наличие track")
    public void createOrderWithOneColor() {
        createOrderStep(Arrays.asList("BLACK"));
    }

    @Test
    @DisplayName("Создание заказа с двумя цветами")
    @Description("Создаем заказ с двумя цветами и проверяем наличие track")
    public void createOrderWithBothColors() {
        createOrderStep(Arrays.asList("BLACK", "GREY"));
    }

    @Test
    @DisplayName("Создание заказа без указания цвета")
    @Description("Создаем заказ без указания цвета и проверяем наличие track")
    public void createOrderWithoutColor() {
        createOrderStep(null);
    }

    @Step("Создание заказа с цветами: {colors}")
    private void createOrderStep(List<String> colors) {
        Order order = new Order("Naruto", "Uzumaki", "Konoha, 142", 1,
                "+7 800 355 35 35", 5, "2025-10-10", "Saske, come back", colors);

        orderClient.createOrder(order)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
