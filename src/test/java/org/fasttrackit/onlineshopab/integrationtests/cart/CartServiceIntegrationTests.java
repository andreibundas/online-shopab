package org.fasttrackit.onlineshopab.integrationtests.cart;

import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.integrationtests.steps.ProductTestSteps;
import org.fasttrackit.onlineshopab.integrationtests.steps.UserTestSteps;
import org.fasttrackit.onlineshopab.service.CartService;
import org.fasttrackit.onlineshopab.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopab.transfer.cart.CartResponse;
import org.fasttrackit.onlineshopab.transfer.cart.ProductInCart;
import org.fasttrackit.onlineshopab.transfer.product.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserTestSteps userTestSteps;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void addProductToCart_whenValidRequest_thenProductsAreAddedToCart() {
        User user = userTestSteps.createUser();
        ProductResponse product = productTestSteps.createProduct();


        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());


        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(request.getUserId()));

        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));

        ProductInCart productInCart = cart.getProducts().iterator().next();

        assertThat(productInCart, notNullValue());
        assertThat(productInCart.getId(), is(product.getId()));
        assertThat(productInCart.getName(), is(product.getName()));
        assertThat(productInCart.getPrice(), is(product.getPrice()));
        assertThat(productInCart.getImageUrl(), is(product.getImageUrl()));

    }

}
