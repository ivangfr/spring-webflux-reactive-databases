package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateNotificationRequest;
import com.ivanfranchin.clientshell.dto.NotificationResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/notifications")
public interface NotificationApiClient {

    @GetExchange("/{id}")
    Mono<NotificationResponse> getNotification(@PathVariable String id);

    @GetExchange
    Flux<NotificationResponse> getNotifications(@RequestParam String orderId);

    @PostExchange
    Mono<NotificationResponse> createNotification(@RequestBody CreateNotificationRequest createNotificationRequest);
}
