package com.bookstore.order.controller;

import com.bookstore.order.dto.OrderRequestDTO;
import com.bookstore.order.entity.Order;
import com.bookstore.order.service.OrderService;
import org.springframework.web.bind.annotation.*;
import com.bookstore.order.dto.ApiResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<Order> createOrder(@RequestBody OrderRequestDTO request) {

        Order order = orderService.createOrder(request);

        return new ApiResponse<>(
                200,
                "Order created successfully",
                order
        );
    }
}