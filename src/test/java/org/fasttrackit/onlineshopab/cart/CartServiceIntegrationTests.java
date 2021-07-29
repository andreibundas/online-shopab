package org.fasttrackit.onlineshopab.cart;

import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.service.CartService;
import org.fasttrackit.onlineshopab.steps.UserTestSteps;
import org.fasttrackit.onlineshopab.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopab.transfer.cart.CartResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void addProductToCart_whenValidRequest_thenProductsAreAddedToCart() {
        User user = userTestSteps.createUser();


        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(1L);

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(user.getId());


        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(request.getUserId()));

    }

}
