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
 private final PasswordEncoder passwordEncoder;
 public UserServiceImpl(UserRepository userRepository,
 PasswordEncoder passwordEncoder) {
 this.userRepository = userRepository;
 this.passwordEncoder = passwordEncoder;
 }
 @Override
 public User register(User user) {
 if (userRepository.existsByEmail(user.getEmail())) {
 throw new BadRequestException("Email already exists");
 }
 if (user.getRole() == null) {
 user.setRole("USER");
 }
 user.setPassword(passwordEncoder.encode(user.getPassword()));
 return userRepository.save(user);
 }
 @Override
 public User findByEmail(String email) {
 return userRepository.findByEmail(email)
 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}
