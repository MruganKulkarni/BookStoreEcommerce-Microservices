package com.bookstore.order.client;

import com.bookstore.order.config.FeignConfig;
import com.bookstore.order.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDTO getProductById(@PathVariable Long id);
}