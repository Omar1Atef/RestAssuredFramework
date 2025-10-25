package com.qacart.todo.testcases;

import com.qacart.todo.pojo.Todo;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest
{

    @Test
    public void shouldBeAbleToAddTodoTest()
    {
//        String body = "{\"item\":\"last task\",\"isCompleted\":false}";
        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YjYzN2MzZWMwYjUzMDAxNDFhN2VkMiIsImZpcnN0TmFtZSI6Ik9tYXIiLCJsYXN0TmFtZSI6IkF0ZWYiLCJpYXQiOjE3NDAwNDgyNDZ9.DR09iDJwZhaadqzHJeVkJSidGKgWlsj2xLT7elaUhnc";

        Todo todo =new Todo();
        todo.setItem("Task");
        todo.setIsCompleted(false); //not between "" because it is not string it is boolean

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(todo)
                .auth().oauth2(token) //token eli rg3li fe response el login bta3 email eli ana sh8al b3ml add Todoo mno
                .log().all().
        when()
                .post("/api/v1/tasks").
        then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(201));
        assertThat(response.jsonPath().getString("item"), equalTo("Task"));
        assertThat(response.jsonPath().getBoolean("isCompleted"), equalTo(false));   //false msh string msh htkon fe ""

    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissingInBodyTest()
    {
//        String body = "{\"item\":\"wrong task\"}";
        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YjYzN2MzZWMwYjUzMDAxNDFhN2VkMiIsImZpcnN0TmFtZSI6Ik9tYXIiLCJsYXN0TmFtZSI6IkF0ZWYiLCJpYXQiOjE3NDAwNDgyNDZ9.DR09iDJwZhaadqzHJeVkJSidGKgWlsj2xLT7elaUhnc";

        Todo todo =new Todo();
        todo.setItem("Task");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType("application/json")
                .body(todo)
                .auth().oauth2(token) //token eli rg3li fe response el login bta3 email eli ana sh8al b3ml add Todoo mno
                .log().all().
        when()
                .post("/api/v1/tasks").
        then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(400));
        assertThat(response.jsonPath().getString("message"), equalTo("\"isCompleted\" is required"));

    }

    //Serialization Only With Post and Put Method

    @Test
    public void shouldBeAbleToGetAllTodoTest()
    {

        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YjYzN2MzZWMwYjUzMDAxNDFhN2VkMiIsImZpcnN0TmFtZSI6Ik9tYXIiLCJsYXN0TmFtZSI6IkF0ZWYiLCJpYXQiOjE3NDAwNDgyNDZ9.DR09iDJwZhaadqzHJeVkJSidGKgWlsj2xLT7elaUhnc";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth().oauth2(token) //token eli rg3li fe response el login bta3 email eli ana sh8al b3ml add Todoo mno
                .log().all().
       when()
                .get("/api/v1/tasks").
       then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(200));
    }

    @Test
    public void shouldBeAbleToGetSpecificTodoTest()
    {
        String task_id = "67b8df8bc50d2300148a16eb";
        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YjYzN2MzZWMwYjUzMDAxNDFhN2VkMiIsImZpcnN0TmFtZSI6Ik9tYXIiLCJsYXN0TmFtZSI6IkF0ZWYiLCJpYXQiOjE3NDAwNDgyNDZ9.DR09iDJwZhaadqzHJeVkJSidGKgWlsj2xLT7elaUhnc";

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth().oauth2(token) //token eli rg3li fe response el login bta3 email eli ana sh8al b3ml add Todoo mno
                .log().all().
        when()
                .delete("/api/v1/tasks/" + task_id).
        then()
                .log().all().extract().response();

        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.jsonPath().getString("item"),equalTo("Task"));

    }

    @Test
    public void shouldBeAbleToDeleteTodoTest()
    {
        String task_id = "67b8df8bc50d2300148a16eb";
        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3YjYzN2MzZWMwYjUzMDAxNDFhN2VkMiIsImZpcnN0TmFtZSI6Ik9tYXIiLCJsYXN0TmFtZSI6IkF0ZWYiLCJpYXQiOjE3NDAwNDgyNDZ9.DR09iDJwZhaadqzHJeVkJSidGKgWlsj2xLT7elaUhnc";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .auth().oauth2(token)
                .log().all().
        when()
                .delete("/api/v1/tasks/" + task_id).
        then()
                .log().all()
                .assertThat().statusCode(200);

    }
}
