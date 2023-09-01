package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.repository.CategoryRepository;
import com.quangtoi.good_news.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        Category categorySave = Category.builder()
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .description(category.getDescription())
                .name(category.getName())
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .isActive(category.getIsActive())
                .build();
        return categoryRepository.save(categorySave);
    }

    @Override
    public Category updateCategory(Category category, Long cateId) {
        Category categoryUpdate = categoryRepository.findById(cateId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        categoryUpdate.setId(categoryUpdate.getId());
        categoryUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        categoryUpdate.setDescription(category.getDescription());
        categoryUpdate.setIsActive(category.getIsActive());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteCategory(Long cateId) {
        Category categorySaved = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        categoryRepository.delete(categorySaved);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByIsActive(true);
    }

    @Override
    public Category getCategoryById(Long cateId) {
        return categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
    }
}
