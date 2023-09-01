package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.UserResponse;
import com.quangtoi.good_news.exception.GoodNewsApiException;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserRole;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.repository.UserRoleRepository;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.ImageService;
import com.quangtoi.good_news.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private PasswordEncoder encoder;
    @Autowired
    private ImageService imageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndActive(username, true);
        List<Role> roles = roleRepository.getAllByUser(user.getId());
        Set<GrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserResponse getByUsername(String username) {
        return mapper.map(userRepository.findByUsernameAndActive(username, true), UserResponse.class);
    }

    @Override
    public UserResponse getByEmail(String email) {
        return mapper.map(userRepository.findByEmailAndActive(email, true)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email)), UserResponse.class);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsernameAndActive(username, true);
    }

    @Override
    public UserResponse register(RegisterRequest req, MultipartFile avatar) {
        if (userRepository.existsByUsernameAndActive(req.getUsername(), true)) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Username is already exist");
        }
        if (userRepository.existsByEmailAndActive(req.getEmail(), true)) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Email is already exist");
        }
        User user = User.builder().username(req.getUsername()).address(req.getAddress()).dateOfBirth(req.getDateOfBirth()).email(req.getEmail()).fullName(req.getFullName()).build();
        if (req.getConfirmPassword().equals(req.getPassword())) {
            user.setPassword(encoder.encode(req.getPassword()));
        }
        user.setAvatar(avatar == null ? null : imageService.uploadImage(avatar));
        user.setActive(true);
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
    public UserResponse updateProfile(RegisterRequest req, MultipartFile avatar, User currentUser) {
        if (userRepository.existsByEmailAndActive(req.getEmail(), true)) {
            throw new GoodNewsApiException(HttpStatus.BAD_REQUEST, "Email is already exist");
        }
        User userProfile = userRepository.findByUsernameAndActive(currentUser.getUsername(), true);
        if (userProfile == null) {
            throw new ResourceNotFoundException("User", "username", currentUser.getUsername());
        }
        userProfile.setAddress(req.getAddress());
        userProfile.setDateOfBirth(req.getDateOfBirth());
        userProfile.setEmail(req.getEmail());
        userProfile.setFullName(req.getFullName());
        if (req.getConfirmPassword().equals(req.getPassword())) {
            userProfile.setPassword(encoder.encode(req.getPassword()));
        }
        userProfile.setAvatar(avatar == null ? null : imageService.uploadImage(avatar));

        User userSaved = userRepository.save(userProfile);
        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public UserResponse changePassword(User user, String newPassword) {
        User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
        currentUser.setPassword(encoder.encode(newPassword));
        User userSaved = userRepository.save(currentUser);
        return mapper.map(userSaved, UserResponse.class);
    }

    @Override
    public void updateResetPassword(String token, String email) {
        User user = userRepository.findByEmailAndActive(email, true)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        user.setPasswordResetToken(token);
        userRepository.save(user);
    }

    @Override
    public User getByResetPasswordToken(String resetPasswordToken) {
        return userRepository.findByPasswordResetToken(resetPasswordToken)
                .orElseThrow(() -> new ResourceNotFoundException("User", "password reset token", resetPasswordToken));
    }

    @Override
    public UserResponse getMyAccount(String username) {
        return mapper.map(userRepository.findByUsernameAndActive(username, true), UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsersIsActive() {
        return userRepository.findAllByActive(true)
                .stream()
                .map(u -> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllUsersIsNotActive() {
        return userRepository.findAllByActive(false)
                .stream()
                .map(u -> mapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapper.map(user, UserResponse.class);
    }
}
