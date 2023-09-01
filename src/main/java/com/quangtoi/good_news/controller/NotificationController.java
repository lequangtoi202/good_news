package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Notification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.NotificationService;
import com.quangtoi.good_news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/api/v1/notifications")
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/api/v1/notifications/{notifiId}")
    public ResponseEntity<?> getNotificationById(@PathVariable("notifiId") Long notifiId) {
        return ResponseEntity.ok(notificationService.getNotificationById(notifiId));
    }

    @GetMapping("/api/v1/users/{userId}/notifications")
    public ResponseEntity<?> getNotificationOfUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(notificationService.getAllNotificationsByUserId(userId));
    }

    @PostMapping("/api/v1/users/{userId}/notifications")
    public ResponseEntity<?> addNewNotification(@PathVariable("userId") Long userId, @RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.addNewNotification(userId, notification));
    }

    @DeleteMapping("/api/v1/notifications/{notifiId}")
    public ResponseEntity<?> deleteNotification(@PathVariable("notifiId") Long notifiId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User currentUser = userService.getByUsername(username);
                if (currentUser != null) {
                    try {
                        notificationService.deleteNotification(currentUser.getId(), notifiId);
                        return ResponseEntity.ok("Successfully");
                    } catch (BadCredentialsException e) {
                        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
