package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Category;
import io.opencensus.trace.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActive(boolean isActive);
}
