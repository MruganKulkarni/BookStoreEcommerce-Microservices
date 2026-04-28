package com.bookstore.cart.service;

import com.bookstore.cart.entity.CartItem;
import com.bookstore.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bookstore.cart.client.ProductClient;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductClient productClient;

    public CartService(CartRepository cartRepository,
                       ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    public CartItem addToCart(CartItem item) {

        // Validate product
        Object product = productClient.getProductById(item.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // 🔥 Check existing item
        return cartRepository
                .findByUserIdAndProductId(item.getUserId(), item.getProductId())
                .map(existingItem -> {
                    existingItem.setQuantity(
                            existingItem.getQuantity() + item.getQuantity()
                    );
                    return cartRepository.save(existingItem);
                })
                .orElseGet(() -> cartRepository.save(item));
    }

    public List<CartItem> getCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }
}