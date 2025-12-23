package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.BudgetPlan;
import com.example.demo.model.BudgetSummary;
import com.example.demo.repository.BudgetPlanRepository;
import com.example.demo.repository.BudgetSummaryRepository;
import com.example.demo.service.BudgetSummaryService;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class BudgetSummaryServiceImpl implements BudgetSummaryService {

    private final BudgetPlanRepository planRepository;
    private final BudgetSummaryRepository summaryRepository;

    public BudgetSummaryServiceImpl(
            BudgetPlanRepository planRepository,
            BudgetSummaryRepository summaryRepository) {
        this.planRepository = planRepository;
        this.summaryRepository = summaryRepository;
    }

    @Override
    public BudgetSummary generateSummary(Long budgetPlanId) {
        BudgetPlan plan = planRepository.findById(budgetPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget plan not found"));

        BudgetSummary summary = new BudgetSummary();
        summary.setBudgetPlan(plan);
        summary.setTotalIncome(0.0);
        summary.setTotalExpense(0.0);
        summary.setStatus("GENERATED");

        return summaryRepository.save(summary);
    }

    @Override
    public BudgetSummary getSummary(Long budgetPlanId) {
        BudgetPlan plan = planRepository.findById(budgetPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget plan not found"));

        BudgetSummary summary = summaryRepository.findByBudgetPlan(plan)
                .orElseThrow(() -> new ResourceNotFoundException("Summary not found"));
        
        return summary;
    }
}