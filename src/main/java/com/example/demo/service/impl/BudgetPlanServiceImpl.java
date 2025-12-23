package com.example.demo.service.impl;

import com.example.demo.model.BudgetPlan;
import com.example.demo.model.User;
import com.example.demo.repository.BudgetPlanRepository;
import com.example.demo.service.BudgetPlanService;
import org.springframework.stereotype.Service;

@Service
public class BudgetPlanServiceImpl implements BudgetPlanService {

    private final BudgetPlanRepository planRepository;

    public BudgetPlanServiceImpl(BudgetPlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public BudgetPlan getPlan(User user, Integer month, Integer year) {

        return planRepository
                .findByUserAndMonthAndYear(user, month, year)
                .orElse(null); // FIXED
    }

    @Override
    public BudgetPlan save(BudgetPlan plan) {
        return planRepository.save(plan);
    }
}
