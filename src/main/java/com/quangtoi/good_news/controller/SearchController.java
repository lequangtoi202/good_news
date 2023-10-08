package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.service.SearchService;
import com.quangtoi.good_news.utils.Routing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping(Routing.SEARCH)
    public ResponseEntity<?> getSearchResult(
            @RequestParam("kw") String kw) {
        return ResponseEntity.ok(searchService.getAllSearchResult(kw));
    }
}
