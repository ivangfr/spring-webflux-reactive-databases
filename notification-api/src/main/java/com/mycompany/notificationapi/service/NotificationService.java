package com.mycompany.notificationapi.service;

import com.mycompany.notificationapi.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<Notification> validateAndGetNotification(Long id);

    Flux<Notification> getNotifications();

    Mono<Notification> saveNotification(Notification notification);
}
