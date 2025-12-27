package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(request.password);
        return userService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return new AuthResponse("dummy-jwt-token");
    }
}
