package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.Category;
import com.quangtoi.good_news.utils.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByCategoryAndActive(Category category, boolean isActive);

    @Query("select a from Article a join ArticleTag at on at.articleId = a.id where at.tagId=:tagId and a.active=:isActive")
    List<Article> findAllByTagAndActive(Long tagId, boolean isActive);

    List<Article> findAllByAuthorsAndActive(Authors authors, boolean isActive);

    List<Article> findAllByActive(boolean isActive);

    @Query("SELECT a FROM Article a WHERE a.active = true AND a.category.id = :cateId ORDER BY a.createdAt DESC")
    List<Article> findLimitNewestArticles(@Param("cateId") Long cateId, Pageable pageable);

    List<Article> findAllByActiveAndStatus(boolean isActive, String type);

    @Query(value = "SELECT id, title, 'Bài viết' as type FROM article WHERE title LIKE %:kw%\n" +
            "UNION\n" +
            "SELECT id, name, 'Tag' as type FROM tag WHERE name LIKE %:kw%\n" +
            "UNION\n" +
            "SELECT id, name, 'Danh mục' as type FROM category WHERE name LIKE %:kw%",
            countQuery = "SELECT COUNT(*) FROM (SELECT id FROM article WHERE title LIKE %:kw%\n" +
                    "UNION\n" +
                    "SELECT id FROM tag WHERE name LIKE %:kw%\n" +
                    "UNION\n" +
                    "SELECT id FROM category WHERE name LIKE %:kw%) AS subquery",
            nativeQuery = true)
    Page<Object[]> getAllSearchResult(@Param("kw") String kw, Pageable pageable);
}
