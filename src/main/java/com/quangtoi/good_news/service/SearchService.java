package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.SearchResult;
import org.springframework.data.domain.Page;

import java.util.List;


public interface SearchService {
    List<SearchResult> getAllSearchResult(String kw);
}
