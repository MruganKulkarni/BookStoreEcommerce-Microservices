package com.bookstore.user.controller;

import com.bookstore.user.entity.User;
import com.bookstore.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
            System.out.println("🔥 REGISTER API HIT");
            return userService.register(user);

    }
}