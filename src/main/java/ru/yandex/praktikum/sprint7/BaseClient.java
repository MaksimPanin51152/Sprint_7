package ru.yandex.praktikum.sprint7;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BaseClient {

    static {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    protected Response get(String endpoint) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .put(endpoint);
    }

    protected Response delete(String endpoint) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(endpoint);
    }
}
