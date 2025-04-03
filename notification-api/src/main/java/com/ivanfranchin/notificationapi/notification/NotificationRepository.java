package com.ivanfranchin.notificationapi.notification;

import com.ivanfranchin.notificationapi.notification.model.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {
}
