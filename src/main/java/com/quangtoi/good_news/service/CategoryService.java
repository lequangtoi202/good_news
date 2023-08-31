package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);

    Category updateCategory(Category category, Long cateId);

    void deleteCategory(Long cateId);

    List<Category> getAllCategories();

    Category getCategoryById(Long cateId);
}
