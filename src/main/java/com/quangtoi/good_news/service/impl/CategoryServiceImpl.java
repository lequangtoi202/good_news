package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.repository.CategoryRepository;
import com.quangtoi.good_news.service.CategoryService;
import com.quangtoi.good_news.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public Category addCategory(Category category, MultipartFile image) {
        Category categorySave = Category.builder()
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .description(category.getDescription())
                .name(category.getName())
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .isActive(true)
                .build();
        categorySave.setImage(image == null ? null : imageService.uploadImage(image));
        return categoryRepository.save(categorySave);
    }

    @Override
    public Category updateCategory(Category category, Long cateId) {
        Category categoryUpdate = categoryRepository.findById(cateId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        categoryUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        categoryUpdate.setDescription(category.getDescription());
        categoryUpdate.setActive(category.isActive());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteCategory(Long cateId) {
        Category categorySaved = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        categorySaved.setActive(false);
        categoryRepository.save(categorySaved);
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
