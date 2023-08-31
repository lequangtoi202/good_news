package com.quangtoi.good_news.service;

import com.quangtoi.good_news.pojo.Notification;

import java.util.List;

public interface NotificationService {
    Notification addNewNotification(Long userId, Notification notification);

    void deleteNotification(Long userId, Long notificationId);

    List<Notification> getAllNotifications();

    Notification getNotificationById(Long notificationId);

    List<Notification> getAllNotificationsByUserId(Long userId);
}
