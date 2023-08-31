package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.*;
import com.quangtoi.good_news.repository.*;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Article addArticle(ArticleDto articleDto, Long categoryId, User currentUser, MultipartFile image) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Authors authors = authorRepository.findByUser(currentUser);
        if (authors == null) {
            throw new ResourceNotFoundException("Author", "userId", currentUser.getId());
        }
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_AUTHOR"));
        if (hasRoleAdminOrAuthor) {
            Article article = Article.builder()
                    .active(true)
                    .authors(authors)
                    .category(category)
                    .content(articleDto.getContent())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .description(articleDto.getDescription())
                    .source(articleDto.getSource())
                    .status(articleDto.getStatus())
                    .title(articleDto.getTitle())
                    .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                    .image(image == null ? null : imageService.uploadImage(image))
                    .build();
            return articleRepository.save(article);
        }
        throw new BadCredentialsException("You don not have permission to add new article");
    }

    @Override
    public Article updateArticle(ArticleDto articleDto, Long articleId, User currentUser) {
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", articleDto.getCategoryId()));
        Authors authors = authorRepository.findById(articleDto.getAuthorsId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", articleDto.getAuthorsId()));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_AUTHOR"));
        if (hasRoleAdminOrAuthor) {
            article.setContent(articleDto.getContent());
            article.setDescription(articleDto.getDescription());
            article.setSource(articleDto.getSource());
            article.setStatus(articleDto.getStatus());
            article.setTitle(articleDto.getTitle());
            article.setCategory(category);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return articleRepository.save(article);
        }
        throw new BadCredentialsException("You don not have permission to update article");
    }

    @Override
    public void deleteArticle(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_AUTHOR"));
        if (hasRoleAdminOrAuthor) {
            article.setActive(false);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            articleRepository.save(article);
        } else {
            throw new BadCredentialsException("You do not have permission to delete this article");
        }
    }

    @Override
    public boolean addTagToArticle(Long articleId, Long tagId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Tag tag = tagRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = ArticleTag.builder()
                .article(article)
                .tagId(tag.getId())
                .build();
        articleTagRepository.save(articleTag);
        return true;
    }

    @Override
    public boolean deleteTagFromArticle(Long articleId, Long tagId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Tag tag = tagRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = articleTagRepository.findByArticleAndTagId(article, tag.getId());
        articleTagRepository.delete(articleTag);
        return true;
    }

    @Override
    public List<Article> getAllArticles() {
        return null;
    }

    @Override
    public List<Article> getAllArticlesByCategory(Long cateId) {
        return null;
    }

    @Override
    public List<Article> getAllArticlesByTag(Long tagId) {
        return null;
    }

    @Override
    public List<Article> getAllArticlesByAuthor(Long authorId) {
        return null;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return null;
    }
}
