import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.constants.BaseURL;
import org.example.constants.CreateIngredient;
import org.example.constants.CreateUser;
import org.example.object.Order;
import org.example.steps.OrderStep;
import org.example.object.User;
import org.junit.After;
import org.example.steps.UserStep;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.example.constants.ExpectedResponseMessage.CREATE_ORDER_WITHOUT_INGREDIENT;
import static org.example.constants.RandomData.RANDOM_INT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {
    User user;
    Order order;
    String accessToken;
    int ingredientListSize;
    List<String> ingredients;
    List<String> allIngredients;


    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURL.BASE_URL;
        allIngredients = OrderStep.getAllIngredients();

        user = CreateUser.createUser();
        Response response = UserStep.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
    }

    @Test
    @DisplayName("Create order with ingredients by authorized user")
    public void createOrderWithIngredientsAuthUser() {
        ingredientListSize = CreateIngredient.createSizeListIngredient(allIngredients.size());
        ingredients = allIngredients.subList(0, ingredientListSize);
        order = new Order(ingredients);
        OrderStep.createOrder(accessToken, order)
                .then().assertThat().statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Create order with ingredients by an unauthorized user")
    public void createOrderWithIngredientsUnAuthUser() {
        ingredientListSize = CreateIngredient.createSizeListIngredient(allIngredients.size());
        ingredients = allIngredients.subList(0, ingredientListSize);
        order = new Order(ingredients);
        OrderStep.createOrderUnAuth(order)
                .then().assertThat().statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Create order without ingredients by authorized user")
    public void createOrderWithoutIngredientsAuthUser() {
        order = new Order(ingredients);
        OrderStep.createOrder(accessToken, order)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(CREATE_ORDER_WITHOUT_INGREDIENT));
    }

    @Test
    @DisplayName("Create order with an invalid ingredient by an authorized user")
    public void createOrderWithInvalidIngredientsAuthUser() {
        ingredients = new ArrayList<>();
        ingredients.add(RANDOM_INT);
        order = new Order(ingredients);
        OrderStep.createOrder(accessToken, order)
                .then().assertThat().statusCode(HTTP_INTERNAL_ERROR);
    }
    @After
    public void tearDown() {
        UserStep.deleteUser(accessToken);
    }

}
