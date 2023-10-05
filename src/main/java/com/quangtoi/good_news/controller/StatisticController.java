package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/api/v1/statistic/count-all-articles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> countAllArticlesActive() {
        return ResponseEntity.ok(statisticService.countAllArticleActive());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/v1/statistic/most-views-article")
    public ResponseEntity<?> getMostViewsArticle() {
        return ResponseEntity.ok(statisticService.getMostViewsArticle());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/v1/statistic/article-category")
    public ResponseEntity<?> statsArticleCategory() {
        return ResponseEntity.ok(statisticService.statisticArticleCategory());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/v1/statistic/user")
    public ResponseEntity<?> statsUserByRole() {
        return ResponseEntity.ok(statisticService.statisticUserRole());
    }
}
