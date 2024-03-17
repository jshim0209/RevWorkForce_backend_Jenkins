package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.entity.Notification;
import com.revature.RevWorkforce.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public void addNotification(Integer account_id, String description) {
        this.notificationRepository.save(new Notification(account_id, description));
    }

    public void addNotification(Integer account_id, String description, String link) {
        this.notificationRepository.save(new Notification(account_id, description, link));
    }

    public List<Notification> getNotificationsByAccountId(Integer account_id) {
        return this.notificationRepository.findNotificationsByAccountId(account_id);
    }
}
