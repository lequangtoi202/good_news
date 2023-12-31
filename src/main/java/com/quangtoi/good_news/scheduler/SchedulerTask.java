package com.quangtoi.good_news.scheduler;

import com.quangtoi.good_news.service.NotificationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerTask.class);
    @Autowired
    private NotificationService notificationService;

    @Async
    @PostConstruct
    @Scheduled(cron = "0 5 * * * *")
    public void handlePushNotificationToUserFollowTag(){
        logger.debug("RUN JOB: Start create new notification");
        notificationService.handlePushNotificationToUsers();
        logger.debug("END JOB: End create notification");
    }
}
