package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.Notification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("FROM notifications WHERE account_id = :accountId ORDER BY create_date DESC")
    public List<Notification> findNotificationsByAccountId(@Param("accountId") Integer account_id);
}
