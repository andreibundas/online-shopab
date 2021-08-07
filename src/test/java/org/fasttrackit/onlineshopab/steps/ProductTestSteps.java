package org.fasttrackit.onlineshopab.steps;

import org.fasttrackit.onlineshopab.domain.Product;
import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.service.ProductService;
import org.fasttrackit.onlineshopab.service.UserService;
import org.fasttrackit.onlineshopab.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopab.transfer.product.SaveProductRequest;
import org.fasttrackit.onlineshopab.transfer.user.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductTestSteps {

    @Autowired
    private ProductService productService;

    public ProductResponse createProduct() {
        SaveProductRequest request = new SaveProductRequest();

        request.setName("Test Product " + System.currentTimeMillis());
        request.setDescription("Test Description");
        request.setPrice(10.7);
        request.setQuantity(50);

        ProductResponse product = productService.createProduct(request);

       assertThat(product, notNullValue());
       assertThat(product.getId(), notNullValue());
       assertThat(product.getId(), greaterThan(0L));
       assertThat(product.getName(), is(request.getName()));
       assertThat(product.getDescription(), is(request.getDescription()));
       assertThat(product.getPrice(), is(request.getPrice()));
       assertThat(product.getQuantity(), is(request.getQuantity()));

       return product;


    }
}
