package com.quangtoi.good_news.repository;

import com.quangtoi.good_news.pojo.RegisterNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterNotificationRepository extends JpaRepository<RegisterNotification, Long> {

    RegisterNotification findByEmail(String email);
}
