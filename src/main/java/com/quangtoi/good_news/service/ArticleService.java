package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserArticle;
import com.quangtoi.good_news.utils.ArticleStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Article addArticle(ArticleDto articleDto, User currentUser, MultipartFile image);

    Article updateArticle(ArticleDto articleDto, Long articleId, User currentUser);

    Article updateStatusArticle(String status, Long articleId, User currentUser);

    void deleteArticle(Long articleId, User currentUser);

    boolean addTagToArticle(Long articleId, Long tagId);

    boolean deleteTagFromArticle(Long articleId, Long tagId);

    List<Article> getAllArticlesIsActive();

    List<Article> getLimitNewestArticlesIsActive(Long cateId, int limit);

    List<Article> getAllArticlesWithStatusIsActive(String type);

    List<Article> getAllArticlesIsNotActive();

    List<Article> getAllArticlesWithStatusIsNotActive(String type);

    List<Article> getAllArticlesByCategory(Long cateId);

    List<Article> getAllArticlesByTag(Long tagId);

    List<Article> getAllArticlesByAuthor(Long authorId);

    Article getArticleById(Long articleId);

    UserArticle addArticleRead(Long articleId, User user);
}
