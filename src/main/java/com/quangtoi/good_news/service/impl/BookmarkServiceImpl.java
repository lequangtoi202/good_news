package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Bookmark;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.ArticleRepository;
import com.quangtoi.good_news.repository.BookmarkRepository;
import com.quangtoi.good_news.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Bookmark addArticleToBookmark(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Bookmark bookmark = Bookmark.builder()
                .article(article)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .userId(currentUser.getId())
                .build();
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public void deleteArticleFromBookmark(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Bookmark bookmark = bookmarkRepository.findByArticleAndUserId(article, currentUser.getId());
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public List<Bookmark> getAllBookmarksOfUser(Long userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }
}
