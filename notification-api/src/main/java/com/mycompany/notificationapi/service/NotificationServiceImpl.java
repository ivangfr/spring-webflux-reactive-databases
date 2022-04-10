package com.mycompany.notificationapi.service;

import com.mycompany.notificationapi.exception.NotificationNotFoundException;
import com.mycompany.notificationapi.model.Notification;
import com.mycompany.notificationapi.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Mono<Notification> validateAndGetNotification(Long id) {
        return notificationRepository.findById(id).switchIfEmpty(Mono.error(new NotificationNotFoundException(id)));
    }

    @Override
    public Flux<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Mono<Notification> saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
