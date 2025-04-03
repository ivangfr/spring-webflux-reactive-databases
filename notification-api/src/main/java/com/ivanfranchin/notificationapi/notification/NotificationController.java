package com.ivanfranchin.notificationapi.notification;

import com.ivanfranchin.notificationapi.notification.mapper.NotificationMapper;
import com.ivanfranchin.notificationapi.notification.model.Notification;
import com.ivanfranchin.notificationapi.notification.dto.CreateNotificationRequest;
import com.ivanfranchin.notificationapi.notification.dto.NotificationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<NotificationResponse> getNotifications(@RequestParam(required = false) String orderId) {
        Flux<Notification> notificationFlux = notificationService.getNotifications();
        if (StringUtils.hasLength(orderId)) {
            notificationFlux = notificationFlux.filter(notification -> notification.getOrderId().equals(orderId));
        }
        return notificationFlux.map(NotificationResponse::from);
    }

    @GetMapping("/{id}")
    public Mono<NotificationResponse> getNotification(@PathVariable Long id) {
        return notificationService.validateAndGetNotification(id).map(NotificationResponse::from);
    }

    @PostMapping
    public Mono<NotificationResponse> createNotification(@Valid @RequestBody CreateNotificationRequest createNotificationRequest) {
        return notificationMapper.toNotification(createNotificationRequest)
                .flatMap(notification -> notificationService.saveNotification(notification)
                        .map(NotificationResponse::from));
    }
}
