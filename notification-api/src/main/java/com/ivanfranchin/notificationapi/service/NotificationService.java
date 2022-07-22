package com.ivanfranchin.notificationapi.service;

import com.ivanfranchin.notificationapi.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<Notification> validateAndGetNotification(Long id);

    Flux<Notification> getNotifications();

    Mono<Notification> saveNotification(Notification notification);
}
