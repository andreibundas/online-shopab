package org.fasttrackit.onlineshopab.persistence;

import org.fasttrackit.onlineshopab.domain.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    Page<ProductReview> findByProductId(long productId, Pageable pageable);
}
