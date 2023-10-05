package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActive(boolean isActive);
    Page<Category> findAllByIsActive(boolean isActive, Pageable pageable);
    Category findByName(String name);
}
