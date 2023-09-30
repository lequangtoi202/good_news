package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.SearchResult;
import org.springframework.data.domain.Page;


public interface SearchService {
    Page<SearchResult> getAllSearchResult(String kw, int pageSize, int pageNumber);
}
