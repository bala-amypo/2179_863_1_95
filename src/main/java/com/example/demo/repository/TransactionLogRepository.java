package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.TransactionLog;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
 List<TransactionLog> findByUser(User user);
 List<TransactionLog> findByUserAndTransactionDateBetween(
 User user, LocalDate start, LocalDate end);
}
