package com.bookstore.product.controller;

import com.bookstore.product.entity.Product;
import com.bookstore.product.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import com.bookstore.product.dto.ProductRequestDTO;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductRequestDTO request) {

        Product product = new Product(
                null,
                request.getName(),
                request.getPrice()
        );

        return productService.addProduct(product);
    }
}