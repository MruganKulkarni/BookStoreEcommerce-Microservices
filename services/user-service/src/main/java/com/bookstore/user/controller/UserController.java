package com.bookstore.user.controller;

import com.bookstore.user.dto.UserRequestDTO;
import com.bookstore.user.dto.UserResponseDTO;
import com.bookstore.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody UserRequestDTO request) {
        return userService.register(request);
    }
}