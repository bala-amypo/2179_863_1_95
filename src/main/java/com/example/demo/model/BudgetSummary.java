package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BudgetSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BudgetPlan budgetPlan;

    private Double totalIncome;
    private Double totalExpense;
    private String status;
    private LocalDateTime generatedAt;

    @PrePersist
    public void onCreate() {
        generatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public BudgetPlan getBudgetPlan() { return budgetPlan; }
    public String getStatus() { return status; }

    public void setBudgetPlan(BudgetPlan budgetPlan) { this.budgetPlan = budgetPlan; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }
    public void setTotalExpense(Double totalExpense) { this.totalExpense = totalExpense; }
    public void setStatus(String status) { this.status = status; }
}
