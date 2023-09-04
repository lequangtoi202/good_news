package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.*;
import com.quangtoi.good_news.repository.*;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.ImageService;
import com.quangtoi.good_news.utils.ArticleStatus;
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
    @Autowired
    private UserArticleRepository userArticleRepository;

    @Override
    public Article addArticle(ArticleDto articleDto, User currentUser, MultipartFile image) {
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", articleDto.getCategoryId()));
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
                    .source(articleDto.getSource())
                    .status(ArticleStatus.DRAFT.toString())
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
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_AUTHOR"));
        if (hasRoleAdminOrAuthor) {
            article.setContent(articleDto.getContent());
            article.setSource(articleDto.getSource());
            article.setStatus(articleDto.getStatus());
            article.setTitle(articleDto.getTitle());
            article.setStatus(articleDto.getStatus());
            article.setCategory(category);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return articleRepository.save(article);
        }
        throw new BadCredentialsException("You don not have permission to update article");
    }

    @Override
    public Article updateStatusArticle(String status, Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_APPROVER"));
        if (hasRoleAdminOrAuthor) {
            article.setStatus(status);
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
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = ArticleTag.builder()
                .articleId(article.getId())
                .tagId(tag.getId())
                .build();
        articleTagRepository.save(articleTag);
        return true;
    }

    @Override
    public boolean deleteTagFromArticle(Long articleId, Long tagId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = articleTagRepository.findByArticleIdAndTagId(article.getId(), tag.getId());
        articleTagRepository.delete(articleTag);
        return true;
    }

    @Override
    public List<Article> getAllArticlesIsActive() {
        return articleRepository.findAllByActive(true);
    }

    @Override
    public List<Article> getAllArticlesIsNotActive() {
        return articleRepository.findAllByActive(false);
    }

    @Override
    public List<Article> getAllArticlesByCategory(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        return articleRepository.findAllByCategoryAndActive(category, true);
    }

    @Override
    public List<Article> getAllArticlesByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        return articleRepository.findAllByTagAndActive(tag.getId(), true);
    }

    @Override
    public List<Article> getAllArticlesByAuthor(Long authorId) {
        Authors authors = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        return articleRepository.findAllByAuthorsAndActive(authors, true);
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
    }

    @Override
    public UserArticle addArticleRead(Long articleId, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        UserArticle userArticle = new UserArticle();
        userArticle.setArticleId(article.getId());
        userArticle.setUserId(user.getId());
        userArticle.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        userArticle.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return userArticleRepository.save(userArticle);
    }
}
