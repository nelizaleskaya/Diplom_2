package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.object.Order;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.example.constants.BasePath.*;

public class OrderStep {
    @Step("Create order")
    public static Response createOrder(String accessToken, Order order) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(ORDERS_PATH);
        return response;
    }
    @Step("Create order UnAuth")
    public static Response createOrderUnAuth(Order order) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDERS_PATH);
        return response;
    }
    @Step ("Get order with auth user")
    public static Response getOrdersAuthUser(String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS_PATH);
        return response;
    }
    @Step("Get order with UnAuth user")
    public static Response getOrdersUnAuthUser() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS_PATH);
        return response;
    }

    @Step ("Get all ingredients")
    public static List<String> getAllIngredients() {
        List<String> allIngredients = given()
                .header("Content-type", "application/json")
                .when()
                .get(INGREDIENT_PATH)
                .then()
                .extract()
                .path("data._id");
        return allIngredients;
    }

}
