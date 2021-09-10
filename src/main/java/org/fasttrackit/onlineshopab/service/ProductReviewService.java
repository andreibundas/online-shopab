package org.fasttrackit.onlineshopab.service;

import org.fasttrackit.onlineshopab.domain.Product;
import org.fasttrackit.onlineshopab.domain.ProductReview;
import org.fasttrackit.onlineshopab.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopab.persistence.ProductReviewRepository;
import org.fasttrackit.onlineshopab.transfer.productReview.ProductReviewResponse;
import org.fasttrackit.onlineshopab.transfer.productReview.SaveProductReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewService.class);

    private final ProductReviewRepository productReviewRepository;
    private final ProductService productService;

    @Autowired
    public ProductReviewService(ProductReviewRepository productReviewRepository, ProductService productService) {
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
    }

    @Transactional
    public ProductReviewResponse createReview(SaveProductReviewRequest request) {
        LOGGER.info("Creating product review: {} ", request);

        Product product = productService.getProduct(request.getProductId());

        ProductReview review = new ProductReview();
        review.setContent(request.getContent());
        review.setProduct(product);

        ProductReview savedReview = productReviewRepository.save(review);
        return mapProductReviewResponse(savedReview);

    }

    public ProductReview getProductReview(long id) {
        LOGGER.info("Retrieving productReview {}", id);
        return productReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductReview " + id + " does not exist"));
    }

    public ProductReviewResponse getProductReviewResponse(long id) {
        ProductReview productReview = getProductReview(id);

        return mapProductReviewResponse(productReview);
    }

    public ProductReview updateProductReview(long id, SaveProductReviewRequest request) {
        LOGGER.info("Updating productReview {}: {}", id, request);

        ProductReview existingProductReview = getProductReview(id);

        existingProductReview.setContent(request.getContent());
        existingProductReview.setId(request.getProductId());

        return productReviewRepository.save(existingProductReview);
    }

    public ProductReviewResponse updateProductReviewResponse(long id, SaveProductReviewRequest reviewRequest) {
        ProductReview review = updateProductReview(id, reviewRequest);

        return mapProductReviewResponse(review);
    }


    private ProductReviewResponse mapProductReviewResponse(ProductReview review) {
        ProductReviewResponse response = new ProductReviewResponse();
        response.setId(review.getId());
        response.setContent(review.getContent());
        return response;
    }

}