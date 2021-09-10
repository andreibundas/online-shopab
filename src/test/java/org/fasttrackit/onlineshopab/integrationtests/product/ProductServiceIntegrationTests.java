package org.fasttrackit.onlineshopab.integrationtests.product;

import org.fasttrackit.onlineshopab.integrationtests.steps.ProductTestSteps;
import org.fasttrackit.onlineshopab.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void createProduct_whenValidRequest_thenReturnCreatedProduc() {
        productTestSteps.createProduct();
    }


}
