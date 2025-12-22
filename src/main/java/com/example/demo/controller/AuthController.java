package com.example.auth.controller;

import com.example.auth.dto.AuthResponse;
import com.example.auth.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody User user) {
        users.add(user);
        return new AuthResponse("Signup successful", true);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) &&
                u.getPassword().equals(user.getPassword())) {
                return new AuthResponse("Login successful", true);
            }
        }
        return new AuthResponse("Invalid credentials", false);
    }
}
