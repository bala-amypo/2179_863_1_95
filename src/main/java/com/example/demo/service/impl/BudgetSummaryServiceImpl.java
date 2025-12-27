package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BudgetPlan;
import com.example.demo.model.BudgetSummary;
import com.example.demo.model.Category;
import com.example.demo.model.TransactionLog;
import com.example.demo.repository.BudgetPlanRepository;
import com.example.demo.repository.BudgetSummaryRepository;
import com.example.demo.repository.TransactionLogRepository;
import com.example.demo.service.BudgetSummaryService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetSummaryServiceImpl implements BudgetSummaryService {

    private final BudgetSummaryRepository budgetSummaryRepository;
    private final BudgetPlanRepository budgetPlanRepository;
    private final TransactionLogRepository transactionLogRepository;

    public BudgetSummaryServiceImpl(
            BudgetSummaryRepository budgetSummaryRepository,
            BudgetPlanRepository budgetPlanRepository,
            TransactionLogRepository transactionLogRepository) {

        this.budgetSummaryRepository = budgetSummaryRepository;
        this.budgetPlanRepository = budgetPlanRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public BudgetSummary generateSummary(Long budgetPlanId) {

        BudgetPlan plan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Budget plan not found"));

        YearMonth ym = YearMonth.of(plan.getYear(), plan.getMonth());
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<TransactionLog> logs =
                transactionLogRepository.findByUserAndTransactionDateBetween(
                        plan.getUser(), start, end);

        double income = logs.stream()
                .filter(t -> Category.TYPE_INCOME.equals(t.getCategory().getType()))
                .mapToDouble(TransactionLog::getAmount)
                .sum();

        double expense = logs.stream()
                .filter(t -> Category.TYPE_EXPENSE.equals(t.getCategory().getType()))
                .mapToDouble(TransactionLog::getAmount)
                .sum();

        BudgetSummary summary = budgetSummaryRepository
                .findByBudgetPlan(plan)
                .orElse(new BudgetSummary());

        summary.setBudgetPlan(plan);
        summary.setTotalIncome(income);
        summary.setTotalExpense(expense);

        summary.setStatus(
                expense <= plan.getExpenseLimit()
                        ? BudgetSummary.STATUS_UNDER_LIMIT
                        : BudgetSummary.STATUS_OVER_LIMIT
        );

        return budgetSummaryRepository.save(summary);
    }
}
