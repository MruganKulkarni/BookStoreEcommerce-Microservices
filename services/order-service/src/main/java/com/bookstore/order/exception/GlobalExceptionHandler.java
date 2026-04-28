package com.bookstore.order.exception;

import com.bookstore.order.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleProductNotFound(ProductNotFoundException ex) {
        return new ApiResponse<>(
                404,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleGeneric(Exception ex) {
        ex.printStackTrace(); // 🔥 ADD THIS
        return new ApiResponse<>(
                500,
                ex.getMessage(),   // 🔥 SHOW REAL ERROR
                null
        );
    }
}