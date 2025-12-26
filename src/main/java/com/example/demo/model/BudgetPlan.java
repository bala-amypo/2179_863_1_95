package com.example.demo.model;

import jakarta.persistence.*;
import com.example.demo.exception.BadRequestException;

@Entity
public class BudgetPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Integer month;
    private Integer year;
    private Double incomeTarget;
    private Double expenseLimit;

    public BudgetPlan() {}

    public BudgetPlan(Long id, User user, Integer month, Integer year, Double incomeTarget, Double expenseLimit) {
        this.id = id;
        this.user = user;
        this.month = month;
        this.year = year;
        this.incomeTarget = incomeTarget;
        this.expenseLimit = expenseLimit;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Integer getMonth() { return month; }
    public Integer getYear() { return year; }
    public Double getIncomeTarget() { return incomeTarget; }
    public Double getExpenseLimit() { return expenseLimit; }

    public void setUser(User user) { this.user = user; }
    public void setMonth(Integer month) { this.month = month; }
    public void setYear(Integer year) { this.year = year; }
    public void setIncomeTarget(Double incomeTarget) { this.incomeTarget = incomeTarget; }
    public void setExpenseLimit(Double expenseLimit) { this.expenseLimit = expenseLimit; }

    public void validate() {
        if (month == null || month < 1 || month > 12) {
            throw new BadRequestException("Month must be between 1 and 12");
        }
        if (year == null) throw new BadRequestException("Year is required");
        if (incomeTarget != null && incomeTarget < 0) throw new BadRequestException("Income target cannot be negative");
        if (expenseLimit != null && expenseLimit < 0) throw new BadRequestException("Expense limit cannot be negative");
    }
}