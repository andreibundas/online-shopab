package org.fasttrackit.onlineshopab.user;

import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.service.UserService;
import org.fasttrackit.onlineshopab.transfer.SaveUserRequest;
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

    @Test
    public void createUser_whenValidRequest_thenReturnSavedUser() {

        SaveUserRequest request = new SaveUserRequest();
        request.setFirstName("Test First Name");
        request.setLastName("Test Last Name");

        User user = userService.createUser(request);

        assertThat(user, notNullValue());
        assertThat(user.getId(), greaterThan(0L));
        assertThat(user.getFirstName(), is(request.getFirstName()));
        assertThat(user.getLastName(), is(request.getLastName()));
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

}
