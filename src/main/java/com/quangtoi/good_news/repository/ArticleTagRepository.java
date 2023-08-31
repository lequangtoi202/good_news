package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    ArticleTag findByArticleAndTagId(Article article, Long tagId);
}
