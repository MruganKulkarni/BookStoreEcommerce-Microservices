package com.bookstore.order.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private double price;

    public Order() {}

    public Order(Long id, Long productId, Integer quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public Double getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(Double price) { this.price = price; }
}