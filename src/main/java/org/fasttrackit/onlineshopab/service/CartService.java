package org.fasttrackit.onlineshopab.service;

import org.fasttrackit.onlineshopab.domain.Cart;
import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopab.persistence.CartRepository;
import org.fasttrackit.onlineshopab.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopab.transfer.cart.CartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserService userService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Transactional
    public void addProductToCart(AddProductToCartRequest request) {
        LOGGER.info("Adding product to cart: {}", request);

        Cart cart = cartRepository.findById(request.getUserId())
                .orElse(new Cart());

        if(cart.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            cart.setUser(user);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public CartResponse getCart(long userId) {
        LOGGER.info("Retrieving cart for user {}", userId);

       Cart cart = cartRepository.findById(userId)
               .orElseThrow(() -> new ResourceNotFoundException("Cart " + userId + " does not exist"));

       CartResponse cartResponse = new CartResponse();

       cartResponse.setId(cart.getId());

       return cartResponse;
    }
}
