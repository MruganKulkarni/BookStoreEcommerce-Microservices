package com.bookstore.order.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bookstore.order.client.FeignErrorDecoder;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}