package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t join ArticleTag at on at.tagId = t.id where at.articleId = :articleId")
    List<Tag> findAllByArticle(@Param("articleId") Long articleId);

    @Query("SELECT t FROM Tag t")
    List<Tag> findAllTagLimit(Pageable pageable);

}
