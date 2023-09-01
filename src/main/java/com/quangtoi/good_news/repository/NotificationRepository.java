package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n join UserNotification un on un.notificationId=n.id where un.userId=:userId")
    List<Notification> findAllByUser(Long userId);
}
