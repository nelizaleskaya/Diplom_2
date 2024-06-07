package org.example.constants;

import com.github.javafaker.Faker;
import org.example.object.ChangeUserData;
import org.example.object.User;

public class CreateUser {

    static Faker faker = new Faker();

    public static User createUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(4, 8);
        String name = faker.name().fullName();
        return new User(email, password, name);
    }
    public static ChangeUserData createSecondUser() {
        String newEmail = faker.internet().emailAddress();
        String newPassword = faker.internet().password(4, 8);
        String newName = faker.name().fullName();
        return new ChangeUserData(newEmail, newPassword, newName);
    }

}
