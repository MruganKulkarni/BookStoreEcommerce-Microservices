package com.bookstore.wishlist.service;

import com.bookstore.wishlist.entity.WishlistItem;
import com.bookstore.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public List<WishlistItem> getWishlist(Long userId) {
        return repository.findByUserId(userId);
    }

    public WishlistItem addToWishlist(Long userId, Long productId) {

        return repository.findByUserIdAndProductId(userId, productId)
                .orElseGet(() -> {
                    WishlistItem item = new WishlistItem();
                    item.setUserId(userId);
                    item.setProductId(productId);
                    return repository.save(item);
                });
    }

    public void removeFromWishlist(Long userId, Long productId) {
        repository.deleteByUserIdAndProductId(userId, productId);
    }
}