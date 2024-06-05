import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constants.BaseURL;
import org.example.constants.CreateUser;
import org.example.steps.UserStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.object.User;

import static java.net.HttpURLConnection.*;
import static org.example.constants.ExpectedResponseMessage.*;
import static org.example.constants.RandomData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest {

    User user;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURL.BASE_URL;
        user = CreateUser.createUser();
    }

    @Test
    @DisplayName("Create a new user with correct field")
    public void createNewUserWithCorrectField() {
        Response response = UserStep.createUser(user);
        accessToken = response
                .then()
                .extract()
                .path("accessToken")
                .toString();
        response.then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue())
                .and()
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Create same user")
    public void createSameUser() {
        Response response = UserStep.createUser(user);
        accessToken = response
                .then()
                .extract()
                .path("accessToken")
                .toString();
        UserStep.createUser(user)
                .then().assertThat().statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(CREATE_THE_SAME_USER));
    }

    @Test
    @DisplayName("Create user without email")
    public void createUserWithoutEmail() {
        user = new User(RANDOM_PASSWORD, RANDOM_NAME);
        UserStep.createUser(user)
                .then()
                .assertThat().statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(CREATE_USER_WITHOUT_FIELD));

    }

    @Test
    @DisplayName("Create user without password")
    public void createUserWithoutPassword() {
        user = new User(RANDOM_EMAIL, RANDOM_NAME);
        UserStep.createUser(user)
                .then()
                .assertThat().statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(CREATE_USER_WITHOUT_FIELD));
    }

    @Test
    @DisplayName("Create user without name")
    public void createUserWithoutName() {
        user = new User(RANDOM_EMAIL, RANDOM_PASSWORD);
        UserStep.createUser(user)
                .then()
                .assertThat().statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(CREATE_USER_WITHOUT_FIELD));

    }

    @After
    public void tearDown() {
        UserStep.deleteUser(accessToken);
    }
}
