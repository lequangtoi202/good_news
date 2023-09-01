package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByCategoryAndActive(Category category, boolean isActive);

    @Query("select a from Article a join ArticleTag at on at.article.id = a.id where at.tagId=:tagId and a.active=:isActive")
    List<Article> findAllByTagAndActive(Long tagId, boolean isActive);

    List<Article> findAllByAuthorsAndActive(Authors authors, boolean isActive);

    List<Article> findAllByActive(boolean isActive);
}
