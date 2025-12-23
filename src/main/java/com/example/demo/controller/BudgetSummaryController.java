package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.model.BudgetSummary;
import com.example.demo.service.BudgetSummaryService;

@RestController
@RequestMapping("/summary")
public class BudgetSummaryController {

    private final BudgetSummaryService summaryService;

    public BudgetSummaryController(BudgetSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping("/generate/{budgetPlanId}")
    public BudgetSummary generateSummary(@PathVariable Long budgetPlanId) {
        return summaryService.generateSummary(budgetPlanId);
    }

    @GetMapping("/{budgetPlanId}")
    public BudgetSummary getSummary(@PathVariable Long budgetPlanId) {
        return summaryService.getSummary(budgetPlanId);
    }
}
