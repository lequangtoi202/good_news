package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.exception.GoodNewsApiException;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.RegisterNotification;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserRole;
import com.quangtoi.good_news.repository.RegisterNotificationRepository;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.repository.UserRoleRepository;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.ImageService;
import com.quangtoi.good_news.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RegisterNotificationRepository registerNotificationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByUsername(username);
        List<Role> roles = roleRepository.getAllByUser(user.getId());
        Set<GrantedAuthority> authorities = roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsernameAndActive(username, true);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndActive(email, true)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsernameAndActive(username, true);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndActive(email, true);
    }

    @Override
    public UserResponse register(RegisterRequest req, MultipartFile avatar) {
        if (this.existsByUsername(req.getUsername())) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Username has already exist");
        }
        if (this.existsByEmail(req.getEmail())) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Email has already exist");
        }
        User user = User.builder()
                .username(req.getUsername())
                .address(req.getAddress())
                .dateOfBirth(req.getDateOfBirth())
                .email(req.getEmail())
                .fullName(req.getFullName())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .password(req.getConfirmPassword().equals(req.getPassword()) ? encoder.encode(req.getPassword()) : null)
                .avatar(avatar == null ? null : imageService.uploadImage(avatar))
                .active(true)
                .build();
        User userSaved = userRepository.save(user);
        Role role = roleRepository.getByName("ROLE_USER");
        if (role == null) {
            throw new ResourceNotFoundException("Role", "name", "ROLE_USER");
        }
        UserRole userRole = new UserRole();
        userRole.setUser(userSaved);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public UserResponse saveUser(User user) {
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse updateProfile(RegisterRequest req, MultipartFile avatar, User currentUser) {
        User userProfile = userRepository.findByUsernameAndActive(currentUser.getUsername(), true);
        if (userProfile == null) {
            throw new ResourceNotFoundException("User", "username", currentUser.getUsername());
        }
        userProfile.setAddress(req.getAddress());
        userProfile.setDateOfBirth(req.getDateOfBirth());
        userProfile.setEmail(req.getEmail());
        userProfile.setFullName(req.getFullName());
        userProfile.setAvatar(avatar == null ? null : imageService.uploadImage(avatar));

        User userSaved = userRepository.save(userProfile);
        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public UserResponse changePassword(User user, String newPassword) {
        User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        currentUser.setPassword(encoder.encode(newPassword));
        currentUser.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        User userSaved = userRepository.save(currentUser);
        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public void updateResetPassword(String token, String email) {
        User user = this.getByEmail(email);
        user.setPasswordResetToken(token);
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
    }

    @Override
    public User getByResetPasswordToken(String resetPasswordToken) {
        return userRepository.findByPasswordResetToken(resetPasswordToken)
                .orElseThrow(() -> new ResourceNotFoundException("User", "password reset token", resetPasswordToken));
    }

    @Override
    public UserResponse getMyAccount(String username) {
        return mapper.map(this.getByUsername(username), UserResponse.class);
    }

    @Override
    public Page<UserResponse> getAllUsers(boolean active, Pageable pageable) {
        Page<User> userPage = userRepository.findAllByActive(active, pageable);
        return userPage.map(user -> mapper.map(user, UserResponse.class));
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public void softDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setActive(false);
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
    }

    @Override
    public RegisterNotification registerReceiveNotification(RegisterNotification registerNotification) {
        RegisterNotification registerNotificationSaved =
                registerNotificationRepository.findByEmail(registerNotification.getEmail());
        if (registerNotificationSaved != null) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "You have already register!");
        }
        registerNotification.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        registerNotification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
        registerNotification.setActive(true);
        return registerNotificationRepository.save(registerNotification);
    }

    @Override
    public RegisterNotification cancelReceiveNotification(String email) {
        RegisterNotification registerNotification = registerNotificationRepository.findByEmail(email);
        if (registerNotification == null) {
            throw new ResourceNotFoundException("RegisterNotification", "email", email);
        }
        registerNotification.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
        registerNotification.setActive(false);
        return registerNotificationRepository.save(registerNotification);
    }


    @Override
    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.findByUsernameAndActive(username, true);

        if (existUser == null) {
            User newAcc = new User();
            newAcc.setUsername(username);
            newAcc.setEmail(username);

            userRepository.save(newAcc);
        }
    }
}
