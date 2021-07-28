package org.fasttrackit.onlineshopab.web;

import org.fasttrackit.onlineshopab.domain.Cart;
import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.service.CartService;
import org.fasttrackit.onlineshopab.transfer.AddProductToCartRequest;
import org.fasttrackit.onlineshopab.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public ResponseEntity<Cart> addProductToCart(@RequestBody @Valid AddProductToCartRequest request) {
        Cart cart = cartService.addProductToCart(request);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable long userId) {
        CartResponse cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

}
