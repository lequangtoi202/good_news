package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserArticle;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Utility;
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
@CrossOrigin
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/api/v1/articles")
    public ResponseEntity<?> getAllArticles(@RequestParam(value = "active", required = false, defaultValue = "true") final boolean isActive) {
        if (isActive) {
            return ResponseEntity.ok().body(articleService.getAllArticlesIsActive());
        } else {
            return ResponseEntity.ok().body(articleService.getAllArticlesIsNotActive());
        }
    }

    @GetMapping("/api/v1/articles-status")
    public ResponseEntity<?> getAllArticlesPublish(@RequestParam(value = "active", required = false, defaultValue = "true") final boolean isActive,
                                                   @RequestParam(value = "type") String type) {
        if (!Utility.isValidArticleStatus(type)) {
            return ResponseEntity.badRequest().body("Giá trị 'type' không hợp lệ");
        }
        if (isActive) {
            return ResponseEntity.ok().body(articleService.getAllArticlesWithStatusIsActive(type.toUpperCase()));
        } else {
            return ResponseEntity.ok().body(articleService.getAllArticlesWithStatusIsNotActive(type.toUpperCase()));
        }
    }

    @GetMapping("/api/v1/categories/{cateId}/articles/newest")
    public ResponseEntity<?> getAllTopNewestArticleArticles(@PathVariable("cateId") Long cateId,
            @RequestParam(value = "limit", defaultValue = "1") int limit) {

        return ResponseEntity.ok().body(articleService.getLimitNewestArticlesIsActive(cateId, limit));

    }

    @GetMapping("/api/v1/authors/{authorId}/articles")
    public ResponseEntity<?> getAllArticlesByAuthor(@PathVariable("authorId") final Long authorId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByAuthor(authorId));
    }

    @GetMapping("/api/v1/tags/{tagId}/articles")
    public ResponseEntity<?> getAllArticlesByTag(@PathVariable("tagId") final Long tagId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByTag(tagId));
    }

    @GetMapping("/api/v1/categories/{cateId}/articles")
    public ResponseEntity<?> getAllArticlesByCategory(@PathVariable("cateId") final Long cateId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByCategory(cateId));
    }

    @GetMapping("/api/v1/articles/{articleId}")
    public ResponseEntity<?> getAllArticlesById(@PathVariable("articleId") final Long articleId) {
        return ResponseEntity.ok().body(articleService.getArticleById(articleId));
    }

    @PostMapping("/api/v1/articles")
    public ResponseEntity<?> createArticle(@RequestPart("articleRequest") final String articleRequest, @RequestPart("image") MultipartFile image) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    ArticleDto articleDto = objectMapper.readValue(articleRequest, ArticleDto.class);
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
    public ResponseEntity<?> updateArticle(@RequestBody final ArticleDto articleRequest, @PathVariable("articleId") final Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        Article articleSaved = articleService.updateArticle(articleRequest, articleId, currentUser);
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
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") final Long articleId) {
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
    public ResponseEntity<?> addTagToArticle(@RequestParam("articleId") final Long articleId, @RequestParam("tagId") final Long tagId) {
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
    public ResponseEntity<?> deleteTagToArticle(@RequestParam("articleId") final Long articleId, @RequestParam("tagId") final Long tagId) {
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

    @PutMapping("/api/v1/articles/{articleId}/update-status")
    public ResponseEntity<?> updateStatusArticle(@PathVariable("articleId") final Long articleId, @RequestParam("status") String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        if (!Utility.isValidArticleStatus(status)) {
                            return ResponseEntity.badRequest().body("Giá trị 'status' không hợp lệ");
                        }
                        Article article = articleService.updateStatusArticle(status, articleId, currentUser);
                        return ResponseEntity.ok(article);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/v1/articles/{articleId}/user-acticles")
    public ResponseEntity<?> addArticleRead(@PathVariable("articleId") final Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {

                        UserArticle articleRead = articleService.addArticleRead(articleId, currentUser);
                        return ResponseEntity.ok(articleRead);
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
