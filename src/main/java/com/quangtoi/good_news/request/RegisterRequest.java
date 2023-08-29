package com.quangtoi.good_news.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private Timestamp dateOfBirth;
    private String address;
    private String email;
}
