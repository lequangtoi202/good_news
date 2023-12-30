package com.quangtoi.good_news.controller;

import com.quangtoi.good_news.pojo.Notification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.service.NotificationService;
import com.quangtoi.good_news.service.UserService;
import com.quangtoi.good_news.utils.Routing;
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
@CrossOrigin("*")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping(Routing.NOTIFICATIONS)
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping(Routing.NOTIFICATION_BY_ID)
    public ResponseEntity<?> getNotificationById(@PathVariable("notifiId") final Long notifiId) {
        return ResponseEntity.ok(notificationService.getNotificationById(notifiId));
    }

    @GetMapping(Routing.NOTIFICATION_BY_USER)
    public ResponseEntity<?> getNotificationOfUser(@PathVariable("userId") final Long userId) {
        return ResponseEntity.ok(notificationService.getAllNotificationsByUserId(userId));
    }

    @PostMapping(Routing.NOTIFICATION_BY_USER)
    public ResponseEntity<?> addNewNotification(@PathVariable("userId") final Long userId, @RequestBody final Notification notification) {
        return ResponseEntity.ok(notificationService.addNewNotification(userId, notification));
    }

    @DeleteMapping(Routing.NOTIFICATION_BY_ID)
    public ResponseEntity<?> deleteNotification(@PathVariable("notifiId") final Long notifiId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByUsername(username);
        notificationService.deleteNotification(currentUser.getId(), notifiId);
        return ResponseEntity.noContent().build();

    }
}
