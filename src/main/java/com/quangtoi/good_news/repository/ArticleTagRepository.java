package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    ArticleTag findByArticleIdAndTagId(Long articleId, Long tagId);
}
