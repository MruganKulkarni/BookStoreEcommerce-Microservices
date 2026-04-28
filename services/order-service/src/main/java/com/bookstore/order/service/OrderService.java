package com.bookstore.order.service;

import com.bookstore.order.client.ProductClient;
import com.bookstore.order.dto.OrderEvent;
import com.bookstore.order.dto.OrderRequestDTO;
import com.bookstore.order.dto.ProductResponseDTO;
import com.bookstore.order.entity.Order;
import com.bookstore.order.exception.ProductNotFoundException;
import com.bookstore.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.bookstore.order.client.CartClient;
import com.bookstore.order.dto.CartItemDTO;
import java.util.List;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderEventProducer eventProducer;
    private final CartClient cartClient;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient,
                        OrderEventProducer eventProducer,
                        CartClient cartClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.eventProducer = eventProducer;
        this.cartClient = cartClient;
    }
    @Retry(name = "productService", fallbackMethod = "fallbackCreateOrder")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCreateOrder")
    public Order createOrder(OrderRequestDTO request) {

        ProductResponseDTO product = productClient.getProductById(request.getProductId());

        Order order = new Order();
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setPrice(product.getPrice());

        // 🔥 SAVE FIRST
        Order savedOrder = orderRepository.save(order);

        // 🔥 PUBLISH EVENT
        OrderEvent event = new OrderEvent(
                savedOrder.getId(),
                savedOrder.getProductId(),
                "ORDER_PLACED",
                java.time.LocalDateTime.now()
        );

        eventProducer.publishOrderCreated(event);

        return savedOrder;
    }
    public Order fallbackCreateOrder(OrderRequestDTO request, Exception ex) {
        System.out.println(" Fallback triggered for productId=" + request.getProductId()
                + " reason=" + ex.getClass().getSimpleName());

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setPrice(0.0); // safe default

        return orderRepository.save(order);
    }
    public List<Order> placeOrderFromCart(Long userId) {

        // 1. Fetch cart items
        List<CartItemDTO> cartItems = cartClient.getCart(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<Order> orders = new java.util.ArrayList<>();

        for (CartItemDTO item : cartItems) {

            // 2. Get product details
            ProductResponseDTO product =
                    productClient.getProductById(item.getProductId());

            // 3. Create order
            Order order = new Order();
            order.setProductId(product.getId());
            order.setQuantity(item.getQuantity());
            order.setPrice(product.getPrice());

            orders.add(orderRepository.save(order));

            // 4. Remove from cart
            cartClient.removeFromCart(item.getUserId(), item.getProductId());
        }

        return orders;
    }
}