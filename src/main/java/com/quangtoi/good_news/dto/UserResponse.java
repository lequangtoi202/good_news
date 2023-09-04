package com.quangtoi.good_news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private boolean active;
    private String fullName;
    private Timestamp dateOfBirth;
    private String address;
    private String email;
    private String passwordResetToken;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
