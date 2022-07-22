package com.ivanfranchin.notificationapi.repository;

import com.ivanfranchin.notificationapi.model.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {
}
