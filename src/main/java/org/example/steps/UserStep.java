package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.object.User;
import org.example.object.ChangeUserData;

import static io.restassured.RestAssured.given;
import static org.example.constants.BasePath.*;

public class UserStep {
    @Step("Create user")
    public static Response createUser(User user) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(AUTH_REGISTER_PATH);
        return response;
    }

    @Step("Login user")
    public static Response logInUser(User user) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(AUTH_LOGIN_PATH);
        return response;
    }

    @Step("Delete user")
    public static void deleteUser(String accessToken) {
        if (accessToken != null)
            given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(AUTH_USER_PATH);
    }

    @Step("Edit authorized user")
    public static Response editAuthorizedUser(String accessToken, ChangeUserData changeUserData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", accessToken)
                .and()
                .body(changeUserData)
                .when()
                .patch(AUTH_USER_PATH);
        return response;
    }

    @Step("Edit UnAuthorized user")
    public static Response editUnAuthorizedUser(String accessToken, ChangeUserData ChangeUserData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(ChangeUserData)
                .when()
                .patch(AUTH_USER_PATH);
        return response;
    }

}
