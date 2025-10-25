package com.qacart.todo.testcases;

import com.qacart.todo.Pojo.Error;
import com.qacart.todo.Pojo.Todo;
import com.qacart.todo.api.todoApi;
import com.qacart.todo.constants.ErrorMessages;
import com.qacart.todo.steps.todoSteps;
import com.qacart.todo.steps.userSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest
{

    @Test
    public void shouldBeAbleToAddTodoTest()
    {
        String token= userSteps.getUserToken(); //Make register and get token
        Todo todo = todoSteps.generateTodoData();

        Response response = todoApi.addTodo(todo,token); //Call Api and Do Request (given when then)
        Todo returnTodo = response.body().as(Todo.class); //deserialization

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnTodo.getItem(),equalTo(todo.getItem()));
        assertThat(returnTodo.getIsCompleted(),equalTo(todo.getIsCompleted()));
    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissingInBodyTest()
    {

        String token= userSteps.getUserToken();
        Todo todo = todoSteps.generateTodoData();
        Todo t = new Todo();
        t.setItem(todo.getItem());

//        Todo todo =new Todo();  //msh h3rf a3ml call todoSteps.addTodo(); 3lshan bta5od 2 parameters whna ana b3ml pass parameter wa7d
//        todo.setItem("wrong task");

        Response response = todoApi.addTodo(t,token);

        Error error = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(error.getMessage(),equalTo(ErrorMessages.IS_COMPLETE_IS_REQUIRED));
    }


    @Test
    public void shouldBeAbleToGetAllTodoTest()
    {
        String token= userSteps.getUserToken();
        Response response = todoApi.getTodo(null,token);
        assertThat(response.statusCode(),equalTo(200));
    }

    @Test
    public void shouldAbleToGetSpecificTodoTest()
    {
        String token= userSteps.getUserToken();
        Todo todo = todoSteps.generateTodoData();
        String id = todoSteps.getTodoID(todo,token);

        Response response = todoApi.getTodo(id,token);
        Todo returnTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnTodo.getItem(),equalTo(todo.getItem()));
    }

    @Test
    public void shouldBeAbleToDeleteTodoTest()
    {
        String token= userSteps.getUserToken();
        Todo todo = todoSteps.generateTodoData();
        String id = todoSteps.getTodoID(todo,token);

        Response response = todoApi.deleteTodo(id,token);

        Todo returnTodo = response.body().as(Todo.class);

        assertThat(returnTodo.getItem(),equalTo(todo.getItem()));
        assertThat(response.statusCode(),equalTo(200));
    }

}
