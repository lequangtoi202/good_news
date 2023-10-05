package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.RegisterNotification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.request.RegisterRequest;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "active", required = false, defaultValue = "true") final boolean active) {
        if (active) {
            return ResponseEntity.ok(userService.getAllUsersIsActive());
        } else {
            return ResponseEntity.ok(userService.getAllUsersIsNotActive());
        }
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/api/v1/users/me")
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

    @GetMapping("/api/v1/users/by-email")
    public ResponseEntity<?> getByEmail(@RequestParam("email") final String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping("/api/v1/users/by-username")
    public ResponseEntity<?> getByUsername(@RequestParam("username") final String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PutMapping("/api/v1/users")
    public ResponseEntity<?> updateProfile(
            @RequestPart("registerReq") final RegisterRequest req,
            @RequestPart("avatar") final  MultipartFile avatar) {
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

    @PostMapping("/api/v1/users/forgot-password")
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

    @GetMapping("/api/v1/users/reset-password")
    public ResponseEntity<String> checkTokenIsValid(@RequestParam final String token) {
        User user = userService.getByResetPasswordToken(token);
        if (user != null) {
            return ResponseEntity.ok().body("Token is valid!");
        }
        return ResponseEntity.badRequest().body("Token is invalid!");
    }

    @PostMapping("/api/v1/users/reset-password")
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

    @PostMapping("/api/v1/users/change-password")
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

    @PostMapping("/api/v1/users/register-receive-notification")
    public ResponseEntity<?> registerReceiveNotification(@Valid @RequestBody RegisterNotification registerNotification) {
        return ResponseEntity.ok(userService.registerReceiveNotification(registerNotification));
    }

    @DeleteMapping("/api/v1/users/cancel-receive-notification")
    public ResponseEntity<?> cancelReceiveNotification(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.cancelReceiveNotification(email));
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
