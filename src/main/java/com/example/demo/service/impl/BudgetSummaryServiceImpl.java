package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.BudgetPlan;
import com.example.demo.model.BudgetSummary;
import com.example.demo.model.Category;
import com.example.demo.model.TransactionLog;
import com.example.demo.repository.BudgetPlanRepository;
import com.example.demo.repository.BudgetSummaryRepository;
import com.example.demo.repository.TransactionLogRepository;
import com.example.demo.service.BudgetSummaryService;
import com.example.demo.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetSummaryServiceImpl implements BudgetSummaryService {
    private final BudgetSummaryRepository budgetSummaryRepository;
    private final BudgetPlanRepository budgetPlanRepository;
    private final TransactionLogRepository transactionLogRepository;

    // Fixed constructor order to match line 66 of tests
    public BudgetSummaryServiceImpl(BudgetSummaryRepository budgetSummaryRepository, 
                                    BudgetPlanRepository budgetPlanRepository, 
                                    TransactionLogRepository transactionLogRepository) {
        this.budgetSummaryRepository = budgetSummaryRepository;
        this.budgetPlanRepository = budgetPlanRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public BudgetSummary generateSummary(Long budgetPlanId) {
        BudgetPlan plan = planRepository.findById(budgetPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget plan not found"));

        LocalDate start = LocalDate.of(plan.getYear(), plan.getMonth(), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<TransactionLog> transactions = transactionLogRepository.findByUserAndTransactionDateBetween(plan.getUser(), start, end);

        double totalIncome = transactions.stream()
                .filter(t -> Category.TYPE_INCOME.equals(t.getCategory().getType()))
                .mapToDouble(TransactionLog::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> Category.TYPE_EXPENSE.equals(t.getCategory().getType()))
                .mapToDouble(TransactionLog::getAmount)
                .sum();

        BudgetSummary summary = summaryRepository.findByBudgetPlan(plan).orElse(new BudgetSummary());
        summary.setBudgetPlan(plan);
        summary.setTotalIncome(totalIncome);
        summary.setTotalExpense(totalExpense);
        
        if (totalExpense <= plan.getExpenseLimit()) {
            summary.setStatus(BudgetSummary.STATUS_UNDER_LIMIT);
        } else {
            summary.setStatus(BudgetSummary.STATUS_OVER_LIMIT);
        }

        return summaryRepository.save(summary);
    }

    @Override
    public BudgetSummary getSummary(Long budgetPlanId) {
        BudgetPlan plan = planRepository.findById(budgetPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget plan not found"));
        return summaryRepository.findByBudgetPlan(plan)
                .orElseThrow(() -> new ResourceNotFoundException("Summary not found"));
    }
}