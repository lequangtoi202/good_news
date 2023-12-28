package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.enumeration.ERoleName;
import com.quangtoi.good_news.exception.ForbiddenException;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Article;
import com.quangtoi.good_news.pojo.Bookmark;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.ArticleRepository;
import com.quangtoi.good_news.repository.BookmarkRepository;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Bookmark addArticleToBookmark(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Bookmark bookmarkExist = bookmarkRepository.findByArticleAndUserId(article, currentUser.getId());
        if (bookmarkExist != null) {
            bookmarkRepository.delete(bookmarkExist);
            return null;
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .article(article)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .userId(currentUser.getId())
                    .build();
            return bookmarkRepository.save(bookmark);
        }
    }

    @Override
    public void deleteArticleFromBookmark(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Bookmark bookmark = bookmarkRepository.findByArticleAndUserId(article, currentUser.getId());
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public void deleteBookmark(Long bookmarkId, User currentUser) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark", "id", bookmarkId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        Set<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        boolean hasRoleAdmin = roleNames.contains(ERoleName.ROLE_ADMIN.toString());
        if (!hasRoleAdmin) {
            throw new ForbiddenException("Insufficient privilege");
        }
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public List<Bookmark> getAllBookmarksOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return bookmarkRepository.findAllByUserId(userId);
    }

    @Override
    public Page<Bookmark> getAllBookmarks(Pageable pageable) {
        return bookmarkRepository.findAll(pageable);
    }
}
