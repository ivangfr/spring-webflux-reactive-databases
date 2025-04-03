package com.ivanfranchin.notificationapi.notification;

import com.ivanfranchin.notificationapi.notification.exception.NotificationNotFoundException;
import com.ivanfranchin.notificationapi.notification.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Mono<Notification> validateAndGetNotification(Long id) {
        return notificationRepository.findById(id).switchIfEmpty(Mono.error(new NotificationNotFoundException(id)));
    }

    public Flux<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Mono<Notification> saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
