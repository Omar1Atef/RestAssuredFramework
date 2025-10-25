package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.Pojo.Users;
import com.qacart.todo.api.userApi;
import io.restassured.response.Response;

public class userSteps
{
    //bdl mkont adelo el data static
    public static Users generateDataUser() //not registered yet b3ml generate register data for user
    {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678";

        Users users = new Users();
        users.setFirstName(firstName);
        users.setLastName(lastName);
        users.setEmail(email);
        users.setPassword(password);
        return users;

//        return new Users(firstName,lastName,email,password);
    }

    public static Users getAlreadyRegisteredDataUser()
    {
        Users users = generateDataUser();  //3mlt generate register data for user
        userApi.register(users);  //3mlt Register
        return users;  // rg3t el registered user data
    }

    // bdl mktb token static
    public static String getUserToken() // token byegi b3d user my3ml register
    {
        Users users = generateDataUser();  //3mlt generate register data for user
        Response response = userApi.register(users);  //3mlt Register
        return response.jsonPath().getString("access_token");
    }

    public static String getUserToken(Users users)  //a3mlha zy getTodoID
    {
        Response response = userApi.register(users);  //3mlt Register
        return response.jsonPath().getString("access_token");
    }


}
