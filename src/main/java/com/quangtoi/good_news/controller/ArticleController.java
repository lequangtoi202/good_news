package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/api/v1/articles")
    public ResponseEntity<?> getAllArticles(@RequestParam(value = "active", required = false, defaultValue = "true") boolean isActive) {
        if (isActive) {
            return ResponseEntity.ok().body(articleService.getAllArticlesIsActive());
        } else {
            return ResponseEntity.ok().body(articleService.getAllArticlesIsNotActive());
        }
    }

    @GetMapping("/api/v1/articles/authors/{authorId}")
    public ResponseEntity<?> getAllArticlesByAuthor(@PathVariable("authorId") Long authorId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByAuthor(authorId));
    }

    @GetMapping("/api/v1/articles/tags/{tagId}")
    public ResponseEntity<?> getAllArticlesByTag(@PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByTag(tagId));
    }

    @GetMapping("/api/v1/articles/category/{cateId}")
    public ResponseEntity<?> getAllArticlesByCategory(@PathVariable("cateId") Long cateId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByCategory(cateId));
    }

    @GetMapping("/api/v1/articles/{articleId}")
    public ResponseEntity<?> getAllArticlesById(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok().body(articleService.getArticleById(articleId));
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<?> createArticle(@RequestPart("articleRequest") String articleRequest, @RequestPart("image") MultipartFile image) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    ArticleDto articleDto = objectMapper.convertValue(articleRequest, ArticleDto.class);
                    try {
                        Article articleSaved = articleService.addArticle(articleDto, currentUser, image);
                        return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/api/v1/articles/{articleId}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleDto articleRequest, @PathVariable("articleId") Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    ArticleDto articleDto = objectMapper.convertValue(articleRequest, ArticleDto.class);
                    try {
                        Article articleSaved = articleService.updateArticle(articleDto, articleId, currentUser);
                        return new ResponseEntity<>(articleSaved, HttpStatus.OK);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/articles/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        articleService.deleteArticle(articleId, currentUser);
                        return new ResponseEntity<>("Delete article successfully", HttpStatus.NO_CONTENT);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/v1/articles/add-tag")
    public ResponseEntity<?> addTagToArticle(@RequestParam("articleId") Long articleId, @RequestParam("tagId") Long tagId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        boolean result = articleService.addTagToArticle(articleId, tagId);
                        if (result) {
                            return new ResponseEntity<>("Successfully", HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/api/v1/articles/delete-tag")
    public ResponseEntity<?> deleteTagToArticle(@RequestParam("articleId") Long articleId, @RequestParam("tagId") Long tagId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        boolean result = articleService.deleteTagFromArticle(articleId, tagId);
                        if (result) {
                            return new ResponseEntity<>("Successfully", HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
