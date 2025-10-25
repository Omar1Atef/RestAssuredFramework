package com.qacart.todo.api;

import com.qacart.todo.Pojo.Users;
import com.qacart.todo.RMRequestSpecification.Specs;
import com.qacart.todo.constants.Resources;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class userApi
{

    public static Response register(Users users)
    {
         return given()
                .log().all()
                .spec(Specs.getRequestSpec())
                .body(users).
        when()
                .post(Resources.REGISTER_PATH).
        then()
                .log().all().extract().response();
    }

    public static Response login(Users users)
    {
        return given()
                .spec(Specs.getRequestSpec())
                .body(users)
                .log().all().
        when()
                .post(Resources.LOGIN_PATH).
       then()
                .log().all().extract().response();
    }
}
