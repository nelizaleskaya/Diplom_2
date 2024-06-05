import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constants.CreateUser;
import org.example.steps.UserStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.object.User;

import static java.net.HttpURLConnection.*;
import static org.example.constants.BaseURL.BASE_URL;
import static org.example.constants.ExpectedResponseMessage.LOGIN_INCORRECT_USER;
import static org.example.constants.RandomData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {

    User user;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        user = CreateUser.createUser();
    }


    @Test
    @DisplayName("Login use correct field")
    public void loginWithCorrectField() {
        Response responseCreating = UserStep.createUser(user);
        accessToken = responseCreating.then().extract().path("accessToken").toString();
        Response responseLogin = UserStep.logInUser(user);
        responseLogin.then()
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue())
                .and()
                .body("refreshToken", notNullValue())
                .and()
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Login with incorrect email")
    public void loginWithIncorrectEmail() {
        UserStep.createUser(user);
        User incorrectUser = new User(RANDOM_EMAIL, user.getPassword(), user.getName());
        UserStep.logInUser(incorrectUser)
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(LOGIN_INCORRECT_USER));
    }

    @Test
    @DisplayName("Login with incorrect password")
    public void loginWithIncorrectPassword() {
        UserStep.createUser(user);
        User incorrectUser = new User(user.getEmail(), RANDOM_PASSWORD, user.getName());
        UserStep.logInUser(incorrectUser)
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(LOGIN_INCORRECT_USER));
    }

    @After
    public void tearDown() {
        UserStep.deleteUser(accessToken);
    }

}
