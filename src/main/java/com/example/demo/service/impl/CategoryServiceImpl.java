package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.exception.ConflictException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new ConflictException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
