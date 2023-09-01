package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getByUsername(String username);

    User getByEmail(String email);

    Boolean existsByUsername(String username);

    UserResponse register(RegisterRequest req, MultipartFile avatar);

    UserResponse updateProfile(RegisterRequest req, MultipartFile avatar, User currentUser);

    UserResponse changePassword(User user, String newPassword);

    void updateResetPassword(String token, String email);

    User getByResetPasswordToken(String resetPasswordToken);

    UserResponse getMyAccount(String username);

    List<UserResponse> getAllUsersIsActive();

    List<UserResponse> getAllUsersIsNotActive();

    UserResponse getUserById(Long userId);
}
