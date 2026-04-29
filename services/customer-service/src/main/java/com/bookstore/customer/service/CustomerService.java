package com.bookstore.customer.service;

import com.bookstore.customer.entity.Customer;
import com.bookstore.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Customer getByUserId(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer update(Long userId, Customer updated) {

        Customer existing = getByUserId(userId);

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setAddress(updated.getAddress());

        return repository.save(existing);
    }
}