package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.SearchResult;
import com.quangtoi.good_news.repository.ArticleRepository;
import com.quangtoi.good_news.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<SearchResult> getAllSearchResult(String kw) {
        List<Object[]> results = articleRepository.getAllSearchResult(kw);

        List<SearchResult> searchResults = results.stream().map(result -> {
            SearchResult searchResult = new SearchResult();
            searchResult.setId(Long.valueOf(result[0].toString()));
            searchResult.setName(result[1].toString());
            searchResult.setType(result[2].toString());
            return searchResult;
        }).collect(Collectors.toList());

        return searchResults;
    }
}
