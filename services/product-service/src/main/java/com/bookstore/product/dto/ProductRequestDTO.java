package com.bookstore.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ProductRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private Double price;

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}