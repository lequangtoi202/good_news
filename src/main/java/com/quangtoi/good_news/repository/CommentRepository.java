package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleAndActive(Article article, boolean active);

    List<Comment> findAllByActive(boolean active);

    List<Comment> findAllByParentIdAndActive(Comment parent, boolean active);
}
