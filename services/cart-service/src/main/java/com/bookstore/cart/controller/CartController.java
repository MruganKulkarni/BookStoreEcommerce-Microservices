package com.bookstore.cart.controller;

import com.bookstore.cart.entity.CartItem;
import com.bookstore.cart.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody CartItem item) {
        return cartService.addToCart(item);
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam Long userId,
                                 @RequestParam Long productId) {

        cartService.removeFromCart(userId, productId);
        return "Item removed from cart";
    }
}