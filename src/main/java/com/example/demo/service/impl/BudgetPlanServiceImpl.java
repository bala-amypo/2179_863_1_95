package com.example.demo.service.impl;

import com.example.demo.model.BudgetPlan;
import com.example.demo.model.User;
import com.example.demo.repository.BudgetPlanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BudgetPlanService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class BudgetPlanServiceImpl implements BudgetPlanService {

    private final BudgetPlanRepository planRepository;
    private final UserRepository userRepository;

    public BudgetPlanServiceImpl(BudgetPlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BudgetPlan createBudgetPlan(Long userId, BudgetPlan plan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        plan.setUser(user);
        plan.validate();

        if (planRepository.findByUserAndMonthAndYear(user, plan.getMonth(), plan.getYear()).isPresent()) {
            throw new BadRequestException("Budget plan already exists for this month");
        }

        return planRepository.save(plan);
    }

    @Override
    public BudgetPlan getBudgetPlan(Long userId, Integer month, Integer year) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return planRepository
                .findByUserAndMonthAndYear(user, month, year)
                .orElseThrow(() -> new ResourceNotFoundException("Budget plan not found"));
    }
}