package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="budget_summaries")
public class BudgetSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "plan_id")
    private BudgetPlan budgetPlan;

    private double totalIncome;
    private double totalExpense;
    private String status;
    private LocalDateTime generatedAt;

    public BudgetSummary() {}

    // Required by tests
    public BudgetSummary(long id, BudgetPlan budgetPlan, double totalIncome, double totalExpense, String status, LocalDateTime generatedAt) {
        this.id = id;
        this.budgetPlan = budgetPlan;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.status = status;
        this.generatedAt = generatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BudgetPlan getBudgetPlan() { return budgetPlan; }
    public double getTotalIncome() { return totalIncome; }
    public double getTotalExpense() { return totalExpense; }
    public String getStatus() { return status; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
}