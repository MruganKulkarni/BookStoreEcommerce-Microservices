package com.bookstore.wishlist.controller;

import com.bookstore.wishlist.entity.WishlistItem;
import com.bookstore.wishlist.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<WishlistItem> getWishlist(@PathVariable Long userId) {
        return service.getWishlist(userId);
    }

    @PostMapping("/add")
    public WishlistItem add(@RequestParam Long userId,
                            @RequestParam Long productId) {
        return service.addToWishlist(userId, productId);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam Long userId,
                       @RequestParam Long productId) {
        service.removeFromWishlist(userId, productId);
    }
}