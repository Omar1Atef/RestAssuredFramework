package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.Pojo.Todo;
import com.qacart.todo.api.todoApi;
import com.qacart.todo.api.userApi;
import io.restassured.response.Response;

public class todoSteps {

    public static Todo generateTodoData()
    {
        Faker faker = new Faker();

        String item = faker.book().title();
        boolean isCompleted = false;

        Todo todo = new Todo();
        todo.setItem(item);
        todo.setIsCompleted(isCompleted);
        return todo;

    }

    public static String getTodoID(Todo todo,String token)
    {
        Response response = todoApi.addTodo(todo,token);
        return response.jsonPath().getString("_id");
    }
}
