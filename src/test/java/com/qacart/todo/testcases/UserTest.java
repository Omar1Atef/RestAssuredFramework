package com.qacart.todo.testcases;

import com.qacart.todo.pojo.Error;
import com.qacart.todo.pojo.Users;
import com.qacart.todo.api.userApi;
import com.qacart.todo.constants.ErrorMessages;
import com.qacart.todo.steps.userSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest
{
    @Test
    public void shouldBeAbleToRegisterTest()
    {
        Users users = userSteps.generateDataUser();
        Response response = userApi.register(users);
        Users returnUser = response.body().as(Users.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnUser.getFirstName(),equalTo(users.getFirstName()));
    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmailTest()
    {
        Users users = userSteps.getAlreadyRegisteredDataUser();
        Response response =userApi.register(users);
        Error error = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(error.getMessage(),equalTo(ErrorMessages.EMAIL_ALREADY_REGISTERED));
    }

    @Test
    public void shouldBeAbleToLoginTest()
    {
        Users users = userSteps.getAlreadyRegisteredDataUser(); //3lshan a3ml login lazm akon awl registered
        Users loginUser = new Users(users.getEmail(),users.getPassword());
//tricky e7na mfrod hnb3t users bs users 3bara 3n : email password first name and last name wna 3yz fl login ab3t email and password bs flw b3t users hydeni error f3mlt object gded mn nfs class bs edeto bs email and password
        Response response = userApi.login(loginUser);
        Users returnUser = response.body().as(Users.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnUser.getFirstName(),equalTo(users.getFirstName()));
        assertThat(returnUser.getAccess_token(),not(equalTo("null")));
    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPasswordTest()
    {
        Users users = userSteps.getAlreadyRegisteredDataUser();
        Users loginUser = new Users(users.getEmail(),"87654321");
        Response response = userApi.login(loginUser);
        Error error = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(401));
        assertThat(error.getMessage(),equalTo(ErrorMessages.EMAIL_OR_PASSWORD_WRONG));
    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongEmailTest()
    {
        Users users = userSteps.getAlreadyRegisteredDataUser();
        Users loginUser = new Users("omar@gmail.com", users.getPassword());
        Response response = userApi.login(loginUser);
        Error error = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(error.getMessage(),equalTo(ErrorMessages.LOGIN_WITH_NOT_REGISTERED_EMAIL));
    }

}
