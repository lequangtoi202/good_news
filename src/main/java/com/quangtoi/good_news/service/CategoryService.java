package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category, MultipartFile image);

    Category updateCategory(Category category, Long cateId);

    void deleteCategory(Long cateId);

    List<Category> getAllCategories();
    Page<Category> getAllCategoriesPageable(Pageable pageable);

    Category getCategoryById(Long cateId);
}
