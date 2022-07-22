package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateNotificationRequest;
import com.ivanfranchin.clientshell.dto.NotificationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NotificationApiClient {

    private final WebClient webClient;

    public NotificationApiClient(@Qualifier("notificationApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<NotificationResponse> getNotification(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(NotificationResponse.class);
    }

    public Flux<NotificationResponse> getNotifications(String orderId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("orderId", orderId).build())
                .retrieve()
                .bodyToFlux(NotificationResponse.class);
    }

    public Mono<NotificationResponse> createNotification(String orderId) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CreateNotificationRequest.of(orderId))
                .retrieve()
                .bodyToMono(NotificationResponse.class);
    }
}
