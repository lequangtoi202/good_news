package com.quangtoi.good_news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorResponse {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private boolean isActive;
    private String fullName;
    private Timestamp dateOfBirth;
    private String address;
    private String email;
    private String authorName;
    private boolean isConfirmed;
    private String passwordResetToken;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
