package com.bookstore.order.client;

import com.bookstore.order.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            return new ProductNotFoundException("Product not found");
        }

        return new Exception("Feign error: " + response.status());
    }
}