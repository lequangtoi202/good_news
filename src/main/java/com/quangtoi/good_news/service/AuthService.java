package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    JwtResponse login(UserDetails userDetails);

    UserResponse userRegister(RegisterRequest registerReq, MultipartFile avatar);
}
