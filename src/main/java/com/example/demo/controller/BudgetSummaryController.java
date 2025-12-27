package com.example.demo.controller;

import com.example.demo.model.BudgetSummary;
import com.example.demo.service.BudgetSummaryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget-summaries")
public class BudgetSummaryController {

    private final BudgetSummaryService budgetSummaryService;

    public BudgetSummaryController(BudgetSummaryService budgetSummaryService) {
        this.budgetSummaryService = budgetSummaryService;
    }

    @GetMapping("/{budgetPlanId}")
    public ResponseEntity<BudgetSummary> getSummary(
            @PathVariable Long budgetPlanId) {

        return ResponseEntity.ok(
                budgetSummaryService.generateSummary(budgetPlanId)
        );
    }
}
