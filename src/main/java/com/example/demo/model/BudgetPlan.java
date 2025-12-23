package com.example.demo.model;

import jakarta.persistence.*;

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

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Integer getMonth() { return month; }
    public Integer getYear() { return year; }

    public void setUser(User user) { this.user = user; }
    public void setMonth(Integer month) { this.month = month; }
    public void setYear(Integer year) { this.year = year; }
    public void setIncomeTarget(Double incomeTarget) { this.incomeTarget = incomeTarget; }
    public void setExpenseLimit(Double expenseLimit) { this.expenseLimit = expenseLimit; }
}
