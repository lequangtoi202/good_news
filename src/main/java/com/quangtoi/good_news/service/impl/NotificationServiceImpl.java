package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Notification;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.pojo.UserNotification;
import com.quangtoi.good_news.repository.NotificationRepository;
import com.quangtoi.good_news.repository.UserNotificationRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Notification addNewNotification(Long userId, Notification notification) {
        Notification notificationSaved = notificationRepository.save(notification);
        UserNotification userNotification = new UserNotification();
        userNotification.setNotificationId(notificationSaved.getId());
        userNotification.setUserId(userId);
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return notificationRepository.findAllByUser(user.getId());
    }
}
