package com.example.demo.controller;

@RestController
public class AuthController{
    @PutMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
