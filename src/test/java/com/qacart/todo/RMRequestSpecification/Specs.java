package com.qacart.todo.RMRequestSpecification;

import com.qacart.todo.constants.TestData;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static RequestSpecification getRequestSpec()
    {
        return   given()
                .baseUri(TestData.BASE_URL)
                .contentType("application/json");
    }
}
