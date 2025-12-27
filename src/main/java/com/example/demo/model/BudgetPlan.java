package com.example.demo.model;

import com.example.demo.exception.BadRequestException;
import jakarta.persistence.*;

@Entity
@Table(name = "budget_plan",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","month","year"}))
public class BudgetPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Integer month;
    private Integer year;
    private Double incomeLimit;
    private Double expenseLimit;

    public BudgetPlan() {}

    public BudgetPlan(Long id, User user, Integer month, Integer year,
                      Double incomeLimit, Double expenseLimit) {
        this.id = id;
        this.user = user;
        this.month = month;
        this.year = year;
        this.incomeLimit = incomeLimit;
        this.expenseLimit = expenseLimit;
    }

    public void validate() {
        if (month < 1 || month > 12) {
            throw new BadRequestException("Invalid month");
        }
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Double getIncomeLimit() { return incomeLimit; }
    public void setIncomeLimit(Double incomeLimit) { this.incomeLimit = incomeLimit; }

    public Double getExpenseLimit() { return expenseLimit; }
    public void setExpenseLimit(Double expenseLimit) { this.expenseLimit = expenseLimit; }
}
