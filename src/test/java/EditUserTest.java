import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constants.CreateUser;
import org.example.object.ChangeUserData;
import org.example.steps.UserStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.object.User;

import static java.net.HttpURLConnection.*;
import static org.example.constants.BaseURL.BASE_URL;
import static org.example.constants.ExpectedResponseMessage.USER_WITHOUT_AUTH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class EditUserTest {

    User user;
    ChangeUserData userEditedData;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        user = CreateUser.createUser();
        userEditedData = CreateUser.createSecondUser();
    }


    @Test
    @DisplayName("Edit email by authorized user")
    public void editEmailAuthUser() {
        Response responseCreating = UserStep.createUser(user);
        accessToken = responseCreating.then().extract().path("accessToken").toString();
        UserStep.editAuthorizedUser(accessToken, userEditedData)
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Edit email of unauthorized user")
    public void editEmailUnAuthorizedUser() {
        Response responseCreating = UserStep.createUser(user);
        accessToken = responseCreating.then().extract().path("accessToken").toString();
        UserStep.editUnAuthorizedUser(accessToken, userEditedData)
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_WITHOUT_AUTH));
    }

    @After
    public void tearDown() {
        UserStep.deleteUser(accessToken);
    }

}
