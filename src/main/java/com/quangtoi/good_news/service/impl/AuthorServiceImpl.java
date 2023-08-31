package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.AuthorResponse;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.AuthorRepository;
import com.quangtoi.good_news.request.AuthorRequest;
import com.quangtoi.good_news.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorResponse registerAuthor(User currentUser, AuthorRequest authorRequest) {
        Authors authors = Authors.builder()
                .isConfirmed(false)
                .authorName(authorRequest.getAuthorName())
                .user(currentUser)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Authors authorsSaved = authorRepository.save(authors);
        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorName(authorsSaved.getAuthorName())
                .createdAt(authorsSaved.getCreatedAt())
                .address(currentUser.getAddress())
                .avatar(currentUser.getAvatar())
                .dateOfBirth(currentUser.getDateOfBirth())
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .isActive(currentUser.isActive())
                .isConfirmed(authorsSaved.isConfirmed())
                .password(currentUser.getPassword())
                .passwordResetToken(currentUser.getPasswordResetToken())
                .updatedAt(authorsSaved.getUpdatedAt())
                .build();
        return authorResponse;
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        return null;
    }

    @Override
    public Authors getAuthorsByUserId(Long userId) {
        return null;
    }
}
