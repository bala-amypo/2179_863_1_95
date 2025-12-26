package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.BudgetSummaryService;
import com.example.demo.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetSummaryServiceImpl implements BudgetSummaryService {
    private final BudgetSummaryRepository summaryRepository;
    private final BudgetPlanRepository planRepository;
    private final TransactionLogRepository transactionRepo;

    public BudgetSummaryServiceImpl(BudgetSummaryRepository summaryRepository, 
                                    BudgetPlanRepository planRepository, 
                                    TransactionLogRepository transactionRepo) {
        this.summaryRepository = summaryRepository;
        this.planRepository = planRepository;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public BudgetSummary generateSummary(Long planId) {
        BudgetPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        List<TransactionLog> logs = transactionRepo.findByUser(plan.getUser());
        
        double income = logs.stream()
                .filter(l -> Category.TYPE_INCOME.equals(l.getType()))
                .mapToDouble(TransactionLog::getAmount).sum();
        
        double expense = logs.stream()
                .filter(l -> Category.TYPE_EXPENSE.equals(l.getType()))
                .mapToDouble(TransactionLog::getAmount).sum();

        BudgetSummary summary = summaryRepository.findByBudgetPlan(plan)
                .orElse(new BudgetSummary());
        
        summary.setBudgetPlan(plan);
        summary.setTotalIncome(income);
        summary.setTotalExpense(expense);
        summary.setGeneratedAt(LocalDateTime.now());
        summary.setStatus(expense > plan.getExpenseLimit() ? 
                BudgetSummary.STATUS_OVER_LIMIT : BudgetSummary.STATUS_UNDER_LIMIT);

        return summaryRepository.save(summary);
    }

    // Fixed: Renamed from getSummaryByPlan to getSummary to match the Interface
    @Override
    public BudgetSummary getSummary(Long planId) {
        BudgetPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        return summaryRepository.findByBudgetPlan(plan)
                .orElseThrow(() -> new ResourceNotFoundException("Summary not found"));
    }
}