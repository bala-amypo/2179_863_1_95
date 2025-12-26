package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.TransactionLog;
import com.example.demo.model.User;
import java.util.List;
import java.time.LocalDate;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByUserId(Long userId);
    List<TransactionLog> findByUserAndTransactionDateBetween(User user, LocalDate start, LocalDate end);
}