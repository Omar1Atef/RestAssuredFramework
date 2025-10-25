package com.qacart.todo.api;

import com.qacart.todo.Pojo.Todo;
import com.qacart.todo.RMRequestSpecification.Specs;
import com.qacart.todo.constants.Resources;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class todoApi
{

    public static Response addTodo(Todo todo,String token)
    {
        return
        given()
                .spec(Specs.getRequestSpec())
                .body(todo)           //serialization
                .auth().oauth2(token) //token eli rg3li fe response el login bta3 email eli ana sh8al b3ml add Todoo mno
                .log().all().
        when()
                .post(Resources.TODOS_PATH).
        then()
                .log().all().extract().response();
    }

    public static Response getTodo(String task_id,String token)
    {
//        String endpoint = (task_id == null) ? "/api/v1/tasks" : "/api/v1/tasks/" + task_id;
        String endpoint = (task_id == null) ? Resources.TODOS_PATH : Resources.TODOS_PATH + "/" + task_id;
        return
        given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .log().all().
        when()
                .get(endpoint).
        then()
                .log().all().extract().response();
    }

    public static Response deleteTodo(String task_id,String token)
    {
        return
        given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .log().all().
        when()
                .delete(Resources.TODOS_PATH + "/" + task_id).
        then()
                .log().all().extract().response();
    }

}
