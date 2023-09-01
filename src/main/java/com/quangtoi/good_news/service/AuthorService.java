package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.AuthorResponse;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.AuthorRequest;

import java.util.List;

public interface AuthorService {
    AuthorResponse registerAuthor(User currentUser, AuthorRequest authorRequest);

    AuthorResponse confirmAuthor(User currentUser, Long authorId);

    List<AuthorResponse> getAllAuthors();

    Authors getAuthorsByUserId(Long userId);

    void deleteAuthorById(User currentUser, Long authorId);
}