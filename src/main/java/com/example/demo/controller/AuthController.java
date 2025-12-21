package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class AuthController{
    @GetMapping("/hello")
    public String sayHello(cart){
        return cart;
    }
}
