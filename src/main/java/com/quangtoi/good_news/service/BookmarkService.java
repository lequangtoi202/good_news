package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Bookmark;
import com.quangtoi.good_news.pojo.User;

import java.util.List;

public interface BookmarkService {
    Bookmark addArticleToBookmark(Long articleId, User currentUser);

    void deleteArticleFromBookmark(Long articleId, User currentUser);

    List<Bookmark> getAllBookmarksOfUser(Long userId);
}
