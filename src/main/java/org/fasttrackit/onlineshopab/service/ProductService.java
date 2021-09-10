package org.fasttrackit.onlineshopab.service;

import org.fasttrackit.onlineshopab.domain.Product;
import org.fasttrackit.onlineshopab.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopab.persistence.ProductRepository;
import org.fasttrackit.onlineshopab.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopab.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopab.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(SaveProductRequest request) {

        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setDescription((request.getDescription()));
        product.setImageUrl(request.getImageUrl());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(product);

        return mapProductResponse(savedProduct);
    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " does not exist"));
    }

    public ProductResponse getProductResponse(long id) {
        Product product = getProduct(id);

        return mapProductResponse(product);
    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products: {}", request);

        Product exampleProduct = new Product();
        exampleProduct.setName(request.getPartialName());
        exampleProduct.setDescription(request.getPartialDescription());
        exampleProduct.setQuantity(request.getMinQuantity());
        exampleProduct.setPrice(request.getPrice());
        exampleProduct.setCarts(null);

        Example<Product> example = Example.of(exampleProduct,
                ExampleMatcher.matchingAny()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));


        Page<Product> productsPage = productRepository.findAll(example, pageable);

        List<ProductResponse> productDtos = new ArrayList<>();

        for (Product product : productsPage.getContent()) {
            ProductResponse productResponse = mapProductResponse(product);

            productDtos.add(productResponse);
        }

        return new PageImpl<>(productDtos, pageable, productsPage.getTotalElements());
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);

        Product existingProduct = getProduct(id);

        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setImageUrl(request.getImageUrl());


//        BeanUtils.copyProperties(request,existingUser);

        return productRepository.save(existingProduct);
    }

    public ProductResponse updateProductResponse(long id, SaveProductRequest request) {
        Product product = updateProduct(id, request);

        return mapProductResponse(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);

        productRepository.deleteById(id);

    }

    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        return productResponse;
    }
}
