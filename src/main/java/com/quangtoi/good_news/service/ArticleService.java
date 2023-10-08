package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    Article addArticle(ArticleDto articleDto, User currentUser, MultipartFile image);

    Article updateArticle(ArticleDto articleDto, Long articleId, User currentUser);

    Article updateStatusArticle(String status, Long articleId, User currentUser);

    void deleteArticle(Long articleId, User currentUser);

    boolean addTagToArticle(Long articleId, Long tagId);

    boolean deleteTagFromArticle(Long articleId, Long tagId);

    Page<Article> getAllArticlesIsActive(Pageable pageable);

    List<Article> getLimitNewestArticlesIsActive(Long cateId, int limit);

    List<Article> getAllArticlesWithStatusIsActive(String type);

    Page<Article> getAllArticlesIsNotActive(Pageable pageable);

    List<Article> getAllArticlesWithStatusIsNotActive(String type);

    List<Article> getAllArticlesByCategory(Long cateId);

    List<Article> getAllArticlesByTag(Long tagId);

    List<Article> getTop3ArticleNewest();

    List<Article> getAllArticlesByAuthor(Long authorId);

    Article getArticleById(Long articleId);

    UserArticle addArticleRead(Long articleId, User user);

    void crawlData(String category, User user) throws IOException;
}
