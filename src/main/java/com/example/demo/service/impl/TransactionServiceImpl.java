package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.model.TransactionLog;
import com.example.demo.model.User;
import com.example.demo.repository.TransactionLogRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TransactionService;
import com.example.demo.exception.ResourceNotFoundException;
@Service
public class TransactionServiceImpl implements TransactionService {
 private final TransactionLogRepository transactionRepo;
 private final UserRepository userRepository;
 public TransactionServiceImpl(TransactionLogRepository transactionRepo,
 UserRepository userRepository) {
 this.transactionRepo = transactionRepo;
 this.userRepository = userRepository;
 }
 @Override
 public TransactionLog addTransaction(Long userId, TransactionLog log) {
 User user = userRepository.findById(userId)
 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 log.setUser(user);
 log.validate();
 return transactionRepo.save(log);
 }
 @Override
 public List<TransactionLog> getUserTransactions(Long userId) {
 User user = userRepository.findById(userId)
 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 return transactionRepo.findByUser(user);
 }
}