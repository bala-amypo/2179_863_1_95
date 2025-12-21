package com.example.demo.controller;

import 

@RestController
public class AuthController{
    @PutMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
