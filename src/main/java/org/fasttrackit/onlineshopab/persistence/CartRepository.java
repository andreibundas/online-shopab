package org.fasttrackit.onlineshopab.persistence;

import org.fasttrackit.onlineshopab.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
