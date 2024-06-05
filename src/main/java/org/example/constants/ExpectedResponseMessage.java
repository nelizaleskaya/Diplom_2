package org.example.constants;

public class ExpectedResponseMessage {
    // записи, кторые должны получить в ответах
    public static final String CREATE_THE_SAME_USER = "User already exists";
    public static final String CREATE_USER_WITHOUT_FIELD = "Email, password and name are required fields";
    public static final String LOGIN_INCORRECT_USER = "email or password are incorrect";
    public static final String USER_WITHOUT_AUTH = "You should be authorised";
    public static final String CREATE_ORDER_WITHOUT_INGREDIENT = "Ingredient ids must be provided";

}
