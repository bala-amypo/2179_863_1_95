package com.example.demo.repository;

import com.example.demo.model.TransactionLog;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByUserAndTransactionDateBetween(User user, LocalDate start, LocalDate end);
    List<TransactionLog> findByUser(User user);
    List<TransactionLog> findByUserId(Long userId); // Fix for TransactionServiceImpl
}