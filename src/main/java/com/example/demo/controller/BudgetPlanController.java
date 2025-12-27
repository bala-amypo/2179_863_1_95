package com.example.demo.controller;

import com.example.demo.model.BudgetPlan;
import com.example.demo.service.BudgetPlanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget-plans")
public class BudgetPlanController {

    private final BudgetPlanService budgetPlanService;

    public BudgetPlanController(BudgetPlanService budgetPlanService) {
        this.budgetPlanService = budgetPlanService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BudgetPlan> create(
            @PathVariable Long userId,
            @RequestBody BudgetPlan plan) {
        return ResponseEntity.ok(budgetPlanService.createBudgetPlan(userId, plan));
    }
}
