package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.ArticleCategoryResponse;
import com.quangtoi.good_news.dto.ArticleView;
import com.quangtoi.good_news.dto.StatisticUserResponse;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.repository.StatisticRepository;
import com.quangtoi.good_news.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public int countAllArticleActive() {
        return statisticRepository.countAllArticleActive();
    }

    @Override
    public ArticleView getMostViewsArticle() {
        return statisticRepository.mostViewsArticle();
    }

    @Override
    public List<ArticleCategoryResponse> statisticArticleCategory() {
        return statisticRepository.getStatisticArticleCategory();
    }

    @Override
    public List<StatisticUserResponse> statisticUserRole() {
        return statisticRepository.getStatisticUserInSystem();
    }
}
