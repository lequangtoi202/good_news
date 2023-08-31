package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.exception.GoodNewsApiException;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.security.JwtTokenProvider;
import com.quangtoi.good_news.service.AuthService;
import com.quangtoi.good_news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public JwtResponse login(UserDetails userDetails) {
        JwtResponse jwtResponse = tokenProvider.generateToken(userDetails);
        return jwtResponse;
    }

    @Override
    public UserResponse userRegister(RegisterRequest registerReq, MultipartFile avatar) {
        if (userService.existsByUsername(registerReq.getUsername())) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Username is already exist");
        }
        return userService.register(registerReq, avatar);
    }
}
