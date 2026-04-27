package com.bookstore.order.dto;

import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    public OrderRequestDTO() {}

    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}