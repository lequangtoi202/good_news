package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.UserArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {
}
