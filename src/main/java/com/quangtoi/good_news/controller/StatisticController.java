package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.service.StatisticService;
import com.quangtoi.good_news.utils.Routing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping(Routing.STAT_COUNT_ARTICLES)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> countAllArticlesActive() {
        return ResponseEntity.ok(statisticService.countAllArticleActive());
    }

    @GetMapping(Routing.STAT_COUNT_USERS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> countAllUsersActive() {
        return ResponseEntity.ok(statisticService.countAllUserActive());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(Routing.STAT_MOST_VIEWS_ARTICLE)
    public ResponseEntity<?> getMostViewsArticle() {
        return ResponseEntity.ok(statisticService.getMostViewsArticle());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(Routing.STAT_CATEGORY_ARTICLE)
    public ResponseEntity<?> statsArticleCategory() {
        return ResponseEntity.ok(statisticService.statisticArticleCategory());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(Routing.STAT_ROLE_USER)
    public ResponseEntity<?> statsUserByRole() {
        return ResponseEntity.ok(statisticService.statisticUserRole());
    }
}
