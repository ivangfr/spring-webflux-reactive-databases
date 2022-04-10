package com.mycompany.notificationapi.repository;

import com.mycompany.notificationapi.model.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {
}
