package org.fasttrackit.onlineshopab.persistence;

import org.fasttrackit.onlineshopab.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
