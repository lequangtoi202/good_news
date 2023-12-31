package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.enumeration.ENotificationType;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.*;
import com.quangtoi.good_news.repository.NotificationRepository;
import com.quangtoi.good_news.repository.TagRepository;
import com.quangtoi.good_news.repository.UserNotificationRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.NotificationService;
import com.quangtoi.good_news.service.TagService;
import com.quangtoi.good_news.service.UserService;
import io.swagger.v3.core.util.Json;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @Override
    public Notification addNewNotification(Long userId, Notification notification) {
        User user = userService.findUserById(userId);
        notification.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        notification.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Notification notificationSaved = notificationRepository.save(notification);

        UserNotification userNotification = new UserNotification();
        userNotification.setNotificationId(notificationSaved.getId());
        userNotification.setUserId(user.getId());
        userNotificationRepository.save(userNotification);
        return notificationSaved;
    }

    @Override
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
        notificationRepository.delete(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        User user = userService.findUserById(userId);
        return notificationRepository.findAllByUser(user.getId());
    }

    @Override
    public void handlePushNotificationToUsers() {
        List<Tag> tags = tagService.getAllTags();
        tags.forEach(tag -> {
            List<User> users = userService.getAllUsersByTag(tag.getId());
            List<Article> articles = articleService.getAllArticlesNewestByTag(tag.getId());
            users.forEach(user -> articles.forEach(article -> {
                JSONObject data = new JSONObject();
                data.put("articleTitle", article.getTitle());
                data.put("articleAuthor", article.getAuthors().getAuthorName());
                String jsonData = data.toJSONString();
                Notification notification = Notification.builder()
                        .title("Bạn có bài viết mới về " + tag.getName())
                        .content(jsonData)
                        .isSent(true)
                        .type(ENotificationType.NEW_ARTICLE.toString())
                        .sentAt(Timestamp.valueOf(LocalDateTime.now()))
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
                Notification notificationSaved = notificationRepository.save(notification);

                UserNotification userNotification = new UserNotification();
                userNotification.setUserId(user.getId());
                userNotification.setNotificationId(notificationSaved.getId());
                userNotificationRepository.save(userNotification);
            }));
        });
    }
}
