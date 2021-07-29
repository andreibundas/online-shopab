package org.fasttrackit.onlineshopab.user;

import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopab.service.UserService;
import org.fasttrackit.onlineshopab.steps.UserTestSteps;
import org.fasttrackit.onlineshopab.transfer.user.SaveUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class UserServiceIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void createUser_whenValidRequest_thenReturnSavedUser() {

        userTestSteps.createUser();
    }

    @Test
    public void createUser_whenMissingFirstName_thenThrowException() {
        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName(null);
        request.setLastName("Test Last Name2");

        Exception exception = null;

        try {
            userService.createUser(request);
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception, notNullValue());
        assertThat("Unexpected exception type. ", exception instanceof TransactionSystemException);

    }

    @Test
    public void getUser_whenExistingUser_thenReturnUser() {

        User createdUser = userTestSteps.createUser();
        User userResponse = userService.getUser(createdUser.getId());

        assertThat(userResponse, notNullValue());
        assertThat(userResponse.getId(), is(createdUser.getId()));
        assertThat(userResponse.getFirstName(), is(createdUser.getFirstName()));
        assertThat(userResponse.getLastName(), is(createdUser.getLastName()));

    }

    @Test
    public void getUser_whenNonExistingUser_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.getUser(99999));
    }

    @Test
    public void updateUser_whenValidRequest_thenReturnUpdatedUser() {
        User createdUser = userTestSteps.createUser();

        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName(createdUser.getFirstName() + " Updated");
        request.setLastName(createdUser.getLastName() + " Updated");

        User updatedUser = userService.updateUser(createdUser.getId(), request);

        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), is(createdUser.getId()));
        assertThat(updatedUser.getFirstName(), is(request.getFirstName()));
        assertThat(updatedUser.getLastName(), is(request.getLastName()));

    }

    @Test
    public void updateUser_whenNonExistingUser_thenThrowResourceNotFoundException() {
        User createdUser = userTestSteps.createUser();

        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName(createdUser.getFirstName() + " Updated");
        request.setLastName(createdUser.getLastName() + " Updated");

        User updatedUser = userService.updateUser(createdUser.getId(), request);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.updateUser(9999, request));
    }

    @Test
    public void deleteUser_whenExistingUser_thenTheUserIsDeleted() {
        User createdUser = userTestSteps.createUser();
        userService.deleteUser(createdUser.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(createdUser.getId()));

    }

}
