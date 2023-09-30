package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.JwtResponse;
import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.dto.enumeration.ProviderType;
import com.quangtoi.good_news.exception.GoodNewsApiException;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.request.ThirdPartyRequest;
import com.quangtoi.good_news.security.JwtTokenProvider;
import com.quangtoi.good_news.service.AuthService;
import com.quangtoi.good_news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Override
    public JwtResponse thirdPartyService(ThirdPartyRequest thirdPartyPayload) {
        String userEmail = thirdPartyPayload.getEmail();
        String username = thirdPartyPayload.getUsername();
        String fullName = thirdPartyPayload.getFullName();
        String avatar = thirdPartyPayload.getAvatar();
        User user = userService.getByEmail(userEmail);
        if (user == null) {
            User newUser = User.builder()
                    .username(username)
                    .email(userEmail)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                    .fullName(fullName)
                    .provider(String.valueOf(ProviderType.GOOGLE))
                    .avatar(avatar).build();
            userService.saveUser(newUser);
        }
        final UserDetails userDetails = userService.loadUserByUsername(username);
        JwtResponse jwtResponse = this.login(userDetails);
        return jwtResponse;
    }
}
