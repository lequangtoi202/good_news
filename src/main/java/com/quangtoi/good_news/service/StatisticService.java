package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.ArticleCategoryResponse;
import com.quangtoi.good_news.dto.ArticleView;
import com.quangtoi.good_news.dto.StatisticUserResponse;

import java.util.List;

public interface StatisticService {

    int countAllArticleActive();

    ArticleView getMostViewsArticle();

    List<ArticleCategoryResponse> statisticArticleCategory();

    List<StatisticUserResponse> statisticUserRole();
}
