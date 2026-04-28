package com.bookstore.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.bookstore.order.dto.CartItemDTO;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

    @GetMapping("/cart/{userId}")
    List<CartItemDTO> getCart(@PathVariable("userId") Long userId);

    @DeleteMapping("/cart/remove")
    void removeFromCart(@RequestParam Long userId,
                        @RequestParam Long productId);
}