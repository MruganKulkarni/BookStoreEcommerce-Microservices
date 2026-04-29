package com.bookstore.customer.controller;

import com.bookstore.customer.entity.Customer;
import com.bookstore.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return service.create(customer);
    }

    @GetMapping("/{userId}")
    public Customer get(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PutMapping("/{userId}")
    public Customer update(@PathVariable Long userId,
                           @RequestBody Customer customer) {
        return service.update(userId, customer);
    }
}