package com.example.demo.repository;

import com.example.demo.model.BudgetPlan;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetPlanRepository extends JpaRepository<BudgetPlan, Long> {

    List<BudgetPlan> findByUserId(Long userId);

    Optional<BudgetPlan> findByUserAndMonthAndYear(
            User user,
            Integer month,
            Integer year
    );
}
