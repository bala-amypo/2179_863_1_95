package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ConflictException("Email already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }
}
