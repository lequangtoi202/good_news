package com.quangtoi.good_news.repository.impl;

import com.quangtoi.good_news.dto.ArticleCategoryResponse;
import com.quangtoi.good_news.dto.ArticleView;
import com.quangtoi.good_news.dto.StatisticUserResponse;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.repository.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class StatisticRepositoryImpl implements StatisticRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int countAllArticleActive() {
        String jpql = "SELECT COUNT(a) FROM Article a where a.active=true";
        Query query = entityManager.createQuery(jpql);
        Long value = (Long) query.getSingleResult();
        return value.intValue();
    }

    @Override
    public int countAllUserActive() {
        String jpql = "SELECT COUNT(u) FROM User u where u.active=true";
        Query query = entityManager.createQuery(jpql);
        Long value = (Long) query.getSingleResult();
        return value.intValue();
    }

    @Override
    public ArticleView mostViewsArticle() {
        String jpql = "SELECT new com.quangtoi.good_news.dto.ArticleView(a, count(ua)) from Article a join UserArticle ua on ua.articleId=a.id " +
                "group by a order by COUNT(ua) DESC";
        Query query = entityManager.createQuery(jpql);
        query.setMaxResults(1);
        ArticleView result = (ArticleView) query.getSingleResult();
        return result;
    }

    @Override
    public List<StatisticUserResponse> getStatisticUserInSystem() {
        String jpql = "SELECT r.name, COALESCE(COUNT(u), 0) " +
                "FROM Role r " +
                "LEFT JOIN UserRole ur on ur.role.id = r.id " +
                "LEFT JOIN ur.user u on u.id=ur.user.id " +
                "GROUP BY r.name";
        Query query = entityManager.createQuery(jpql);
        List<Object[]> results = query.getResultList();
        List<StatisticUserResponse> statisticUserResponses = new ArrayList<>();

        for (Object[] result : results) {
            String roleName = (String) result[0];
            Long userCount = (Long) result[1];
            StatisticUserResponse statisticUserResponse = new StatisticUserResponse(roleName, userCount);
            statisticUserResponses.add(statisticUserResponse);
        }
        return statisticUserResponses;
    }

    @Override
    public List<ArticleCategoryResponse> getStatisticArticleCategory() {
        String jpql = "SELECT c.name, COALESCE(COUNT(a), 0) " +
                "FROM Category c " +
                "LEFT JOIN Article a on a.category.id = c.id " +
                "GROUP BY c.name";
        Query query = entityManager.createQuery(jpql);
        List<Object[]> results = query.getResultList();
        List<ArticleCategoryResponse> articleCategoryResponses = new ArrayList<>();

        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long articleCount = (Long) result[1];
            ArticleCategoryResponse articleCategoryResponse = new ArticleCategoryResponse(categoryName, articleCount);
            articleCategoryResponses.add(articleCategoryResponse);
        }
        return articleCategoryResponses;
    }
}
