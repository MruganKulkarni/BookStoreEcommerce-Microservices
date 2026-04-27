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
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderEventProducer eventProducer;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient,
                        OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.eventProducer = eventProducer;
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
}