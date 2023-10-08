package com.quangtoi.good_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.RegisterNotification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
import com.quangtoi.good_news.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final JavaMailSender mailSender;

    @GetMapping(Routing.USERS)
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "active", required = false, defaultValue = "true") final boolean active,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNumber") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (active) {
            return ResponseEntity.ok(userService.getAllUsersIsActive(pageable));
        } else {
            return ResponseEntity.ok(userService.getAllUsersIsNotActive(pageable));
        }
    }

    @GetMapping(Routing.USER_BY_ID)
    public ResponseEntity<?> getUserById(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(Routing.ME)
    public ResponseEntity<?> getMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return ResponseEntity.ok(userService.getMyAccount(username));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(Routing.USER_BY_EMAIL)
    public ResponseEntity<?> getByEmail(@RequestParam("email") final String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping(Routing.USER_BY_USERNAME)
    public ResponseEntity<?> getByUsername(@RequestParam("username") final String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PutMapping(value = Routing.ME, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileMe(
            @RequestParam("userUpdate") final String registerRequest,
            @RequestPart("avatar") final MultipartFile avatar) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest req = objectMapper.readValue(registerRequest, RegisterRequest.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                return ResponseEntity.ok(userService.updateProfile(req, avatar, currentUser));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = Routing.USER_BY_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @PathVariable("userId") final Long userId,
            @RequestParam("userUpdate") final String registerRequest,
            @RequestPart("avatar") final MultipartFile avatar) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterRequest req = objectMapper.readValue(registerRequest, RegisterRequest.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User currentUser = userService.findUserById(userId);
            return ResponseEntity.ok(userService.updateProfile(req, avatar, currentUser));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(Routing.FORGOT_PASSWORD)
    public ResponseEntity<String> processForgotPassword(HttpServletRequest request, @RequestParam final String email) {
        String token = RandomString.make(45);
        try {
            userService.updateResetPassword(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/api/v1/accounts/reset-password?token=" + token;

            sendMail(email, resetPasswordLink);
            return ResponseEntity.ok().body(resetPasswordLink);
        } catch (ResourceNotFoundException | MessagingException e) {
            return ResponseEntity.badRequest().body("Resource not found with email " + email);
        }
    }

    @GetMapping(Routing.RESET_PASSWORD)
    public ResponseEntity<String> checkTokenIsValid(@RequestParam final String token) {
        User user = userService.getByResetPasswordToken(token);
        if (user != null) {
            return ResponseEntity.ok().body("Token is valid!");
        }
        return ResponseEntity.badRequest().body("Token is invalid!");
    }

    @PostMapping(Routing.RESET_PASSWORD)
    public ResponseEntity<String> resetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = userService.getByResetPasswordToken(token);
        if (user != null) {
            userService.changePassword(user, password);
            return ResponseEntity.ok().body("Reset password successfully!");
        }
        return ResponseEntity.badRequest().body("Token is invalid");
    }

    @PostMapping(Routing.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@RequestParam("newPassword") final String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                return ResponseEntity.ok(userService.changePassword(currentUser, newPassword));
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(Routing.RECEIVE_NOTIFICATION)
    public ResponseEntity<?> registerReceiveNotification(@Valid @RequestBody RegisterNotification registerNotification) {
        return ResponseEntity.ok(userService.registerReceiveNotification(registerNotification));
    }

    @DeleteMapping(Routing.CANCEL_RECEIVE_NOTIFICATION)
    public ResponseEntity<?> cancelReceiveNotification(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.cancelReceiveNotification(email));
    }

    @DeleteMapping(Routing.USER_BY_ID)
    public ResponseEntity<?> softDeleteUser(@PathVariable("userId") Long userId) {
        userService.softDeleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @Async
    public void sendMail(final String email, final String resetPasswordLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("2051052140toi@ou.edu.vn");
        helper.setTo(email);

        String subject = "Here is the link to reset your password";
        String content = "<p>Hello,</p>" +
                "<p>You have request to reset your password.</p>"
                + "<p>Click link below to change your password:</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                + "<p>Ignore this email if you do remember your password, or you have not make a request</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
