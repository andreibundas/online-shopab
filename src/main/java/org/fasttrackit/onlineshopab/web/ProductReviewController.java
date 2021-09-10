package org.fasttrackit.onlineshopab.web;

import org.fasttrackit.onlineshopab.service.ProductReviewService;
import org.fasttrackit.onlineshopab.transfer.productReview.ProductReviewResponse;
import org.fasttrackit.onlineshopab.transfer.productReview.SaveProductReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product_reviews")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @Autowired
    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponse> createProductReview(SaveProductReviewRequest request) {
        ProductReviewResponse review = productReviewService.createReview(request);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewResponse> getProductReview(@PathVariable long id) {
        ProductReviewResponse productReview = productReviewService.getProductReviewResponse(id);

        return new ResponseEntity<>(productReview, HttpStatus.OK);
    }


}


