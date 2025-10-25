package com.qacart.todo.testcases;

import com.qacart.todo.pojo.Users;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest
{
    @Test
    public void shouldBeAbleToRegisterTest()
    {
//        String body  = "{\n" +
//                "    \"firstName\" : \"Omar\",\n" +
//                "    \"lastName\" : \"Atef\",\n" +
//                "    \"email\" : \"omaratef67@gmail.com\",\n" +
//                "    \"password\": \"12345678\"\n" +
//                "}";

        Users users = new Users();
        users.setFirstName("Omar");
        users.setLastName("Atef");
        users.setEmail("Mbabbe755@gmail.com");
        users.setPassword("12345678");

        Response response = given()
                .log().all()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(users).

        when()
                .post("/api/v1/users/register").
        then()
                .log().all().extract().response();
        System.out.println(response);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(response.jsonPath().getString("firstName"), equalTo("Omar"));

    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmailTest()
    {
//        String signup = "{\n" +
//                "    \"firstName\" : \"Omar\",\n" +
//                "    \"lastName\" : \"Atef\",\n" +
//                "    \"email\" : \"omaratef676@gmail.com\",\n" +
//                "    \"password\": \"12345678\"\n" +
//                "}";

        Users users = new Users();
        users.setFirstName("Omar");
        users.setLastName("Atef");
        users.setEmail("Mbabbe@gmail.com");
        users.setPassword("12345678");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(users)
                .log().all().
        when()
                .post("/api/v1/users/register").
        then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(400));
        assertThat(response.jsonPath().getString("message"), equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldBeAbleToLoginTest()
    {
//        String login = "{\n" +
//                "\"email\":\"omaratef676@gmail.com\",\n" +
//                "\"password\":\"12345678\"\n" +
//                "}\n";

        Users users = new Users("Mbabbe@gmail.com","12345678");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(users)
                .log().all().
                when()
                .post("/api/v1/users/login").
                then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.jsonPath().getString("firstName"), equalTo("Omar"));
        assertThat(response.jsonPath().getString("access_token"),not(equalTo(null))); //3lshan access token byt8yr kol mra b3ml login

    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPasswordTest()
    {
//        String login = "{\"email\":\"omaratef676@gmail.com\",\"password\":\"123456789\"}";

        Users users = new Users("Mbabbe@gmail.com","123456789");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(users)
                .log().all().
                when()
                .post("/api/v1/users/login").
                then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(401));
        assertThat(response.jsonPath().getString("message"), equalTo("The email and password combination is not correct, please fill a correct email and password"));

    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongEmailTest()
    {
//        String login = "{\"email\":\"omaratf676@gmail.com\",\"password\":\"12345678\"}";

        Users users = new Users("Mbabbee@gmail.com","123456789");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(users)
                .log().all().
                when()
                .post("/api/v1/users/login").
                then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(400));
        assertThat(response.jsonPath().getString("message"), equalTo("We could not find the email in the database"));

    }
}
