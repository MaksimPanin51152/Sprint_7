package ru.yandex.praktikum.sprint7.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.sprint7.OrderClient;
import ru.yandex.praktikum.sprint7.Order;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderClient orderClient = new OrderClient();
    private Integer track;

    @Parameterized.Parameter
    public String[] colors;

    @Parameterized.Parameters(name = "Цвета заказа: {0}")
    public static Collection<Object[]> getColors() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}  // без цвета
        });
    }

    @After
    public void tearDown() {
        if (track != null) {
            try {
                orderClient.cancelOrder(track)
                        .then()
                        .statusCode(200);
            } catch (AssertionError ignored) {
                // Игнорируем 404 если заказ уже отменен
            }
        }
    }

    @Test
    @Description("Создание заказа с цветами")
    public void createOrderWithColors() {
        Order order = new Order();
        if (colors != null) {
            order.setColor(List.of(colors));
        }

        Response response = orderClient.createOrder(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());

        track = response.path("track");
    }
}
