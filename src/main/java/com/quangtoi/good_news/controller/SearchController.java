package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/api/v1/search")
    public ResponseEntity<?> getSearchResult(
            @RequestParam("kw") String kw,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNumber") int pageNumber) {
        return ResponseEntity.ok(searchService.getAllSearchResult(kw, pageSize, pageNumber));
    }
}
