package com.quangtoi.good_news.service;

import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.pojo.RegisterNotification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getByUsername(String username);

    User getByEmail(String email);

    User findUserById(Long userId);

    Boolean existsByUsername(String username);

    UserResponse register(RegisterRequest req, MultipartFile avatar);

    UserResponse saveUser(User user);

    UserResponse updateProfile(RegisterRequest req, MultipartFile avatar, User currentUser);

    UserResponse changePassword(User user, String newPassword);

    void updateResetPassword(String token, String email);

    User getByResetPasswordToken(String resetPasswordToken);

    UserResponse getMyAccount(String username);

    Page<UserResponse> getAllUsers(boolean active, Pageable pageable);

    UserResponse getUserById(Long userId);

    void softDeleteUser(Long userId);

    RegisterNotification registerReceiveNotification(RegisterNotification registerNotification);

    RegisterNotification cancelReceiveNotification(String email);

    List<User> getAllUsersByTag(Long tagId);

    void processOAuthPostLogin(String username);
}
