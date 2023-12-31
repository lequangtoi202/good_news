package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserArticle;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
import com.quangtoi.good_news.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping(Routing.ARTICLES)
    public ResponseEntity<?> getAllArticles(
            @RequestParam(value = "active", required = false, defaultValue = "true") final boolean active,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNumber") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(articleService.getAllArticles(pageable, active));

    }

    @GetMapping(Routing.TOP3_ARTICLES)
    public ResponseEntity<?> getTop3Articles() {
        return ResponseEntity.ok(articleService.getTop3ArticleNewest());
    }

    @GetMapping(Routing.ARTICLES_STATUS)
    public ResponseEntity<?> getAllArticlesPublish(@RequestParam(value = "active", required = false, defaultValue = "true") final boolean active,
                                                   @RequestParam(value = "type") String type) {
        if (!Utility.isValidArticleStatus(type)) {
            return ResponseEntity.badRequest().body("Giá trị 'type' không hợp lệ");
        }
        return ResponseEntity.ok().body(articleService.getAllArticlesWithStatus(active, type.toUpperCase()));

    }

    @GetMapping(Routing.NEWEST_ARTICLES_BY_CATEGORY)
    public ResponseEntity<?> getAllTopNewestArticleArticles(@PathVariable("cateId") Long cateId,
                                                            @RequestParam(value = "limit", defaultValue = "1") int limit) {
        return ResponseEntity.ok().body(articleService.getLimitNewestArticles(cateId, limit));

    }

    @GetMapping(Routing.ARTICLES_BY_AUTHOR)
    public ResponseEntity<?> getAllArticlesByAuthor(@PathVariable("authorId") final Long authorId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByAuthor(authorId));
    }

    @GetMapping(Routing.ARTICLES_BY_TAG)
    public ResponseEntity<?> getAllArticlesByTag(@PathVariable("tagId") final Long tagId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByTag(tagId));
    }

    @GetMapping(Routing.ARTICLES_BY_CATEGORY)
    public ResponseEntity<?> getAllArticlesByCategory(@PathVariable("cateId") final Long cateId) {
        return ResponseEntity.ok().body(articleService.getAllArticlesByCategory(cateId));
    }

    @GetMapping(Routing.ARTICLE_BY_ID)
    public ResponseEntity<?> getAllArticlesById(@PathVariable("articleId") final Long articleId) {
        return ResponseEntity.ok().body(articleService.getArticleById(articleId));
    }

    @PostMapping(Routing.ARTICLES)
    public ResponseEntity<?> createArticle(@RequestPart("articleRequest") final String articleRequest, @RequestPart("image") MultipartFile image) throws JsonProcessingException {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        ArticleDto articleDto = objectMapper.readValue(articleRequest, ArticleDto.class);
        Article articleSaved = articleService.addArticle(articleDto, currentUser, image);
        return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
    }

    @PutMapping(Routing.ARTICLE_BY_ID)
    public ResponseEntity<?> updateArticle(@RequestBody final ArticleDto articleRequest, @PathVariable("articleId") final Long articleId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        Article articleSaved = articleService.updateArticle(articleRequest, articleId, currentUser);
        return new ResponseEntity<>(articleSaved, HttpStatus.OK);
    }

    @DeleteMapping(Routing.ARTICLE_BY_ID)
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") final Long articleId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        articleService.deleteArticle(articleId, currentUser);
        return new ResponseEntity<>("Delete article successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping(Routing.ADD_TAG_TO_ARTICLE)
    public ResponseEntity<?> addTagToArticle(@RequestParam("articleId") final Long articleId, @RequestParam("tagId") final Long tagId) {
        boolean result = articleService.addTagToArticle(articleId, tagId);
        if (result) {
            return new ResponseEntity<>("Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(Routing.DELETE_TAG_TO_ARTICLE)
    public ResponseEntity<?> deleteTagToArticle(@RequestParam("articleId") final Long articleId, @RequestParam("tagId") final Long tagId) {
        boolean result = articleService.deleteTagFromArticle(articleId, tagId);
        if (result) {
            return new ResponseEntity<>("Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Routing.UPDATE_STATUS_ARTICLE)
    public ResponseEntity<?> updateStatusArticle(@PathVariable("articleId") final Long articleId, @RequestParam("status") String status) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        if (!Utility.isValidArticleStatus(status)) {
            return ResponseEntity.badRequest().body("Unknown status");
        }
        Article article = articleService.updateStatusArticle(status, articleId, currentUser);
        return ResponseEntity.ok(article);
    }

    @PostMapping(Routing.ADD_READING_TURN)
    public ResponseEntity<?> addArticleRead(@PathVariable("articleId") final Long articleId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        UserArticle articleRead = articleService.addArticleRead(articleId, currentUser);
        return ResponseEntity.ok(articleRead);
    }

    @PostMapping(Routing.CRAWL_DATA_FROM_VNEXPRESS)
    @ResponseBody
    public ResponseEntity<?> crawlArticlesFromVnExpress(@RequestParam("category") String category) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        try {
            articleService.crawlData(category, currentUser);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
