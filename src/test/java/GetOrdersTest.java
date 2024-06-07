import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constants.CreateUser;
import org.example.steps.OrderStep;
import org.example.steps.UserStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.object.User;

import static java.net.HttpURLConnection.*;
import static org.example.constants.BaseURL.BASE_URL;
import static org.example.constants.ExpectedResponseMessage.USER_WITHOUT_AUTH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrdersTest {

    User user;
    String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        user = CreateUser.createUser();
    }

    @After
    public void tearDown() {
        UserStep.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Get orders of authorized user")
    public void getOrdersAuthUser() {
        Response response = UserStep.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        OrderStep.getOrdersAuthUser(accessToken)
                .then()
                .statusCode(HTTP_OK)
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Get orders of unauthorized user")
    public void getOrdersUnAuthUserGet() {
        Response response = UserStep.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        OrderStep.getOrdersUnAuthUser()
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_WITHOUT_AUTH));
    }
}
