package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.dto.ArticleCategoryResponse;
import com.quangtoi.good_news.dto.ArticleView;
import com.quangtoi.good_news.dto.StatisticUserResponse;

import java.util.List;

public interface StatisticRepository {

    int countAllArticleActive();

    int countAllUserActive();

    ArticleView mostViewsArticle();

    List<StatisticUserResponse> getStatisticUserInSystem();

    List<ArticleCategoryResponse> getStatisticArticleCategory();
}
