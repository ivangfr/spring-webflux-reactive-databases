package com.ivanfranchin.notificationapi.notification.mapper;

import com.ivanfranchin.notificationapi.client.CustomerApiClient;
import com.ivanfranchin.notificationapi.client.OrderApiClient;
import com.ivanfranchin.notificationapi.notification.exception.CreateNotificationException;
import com.ivanfranchin.notificationapi.notification.model.Notification;
import com.ivanfranchin.notificationapi.notification.dto.CreateNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class NotificationMapper {

    private final OrderApiClient orderApiClient;
    private final CustomerApiClient customerApiClient;

    public Mono<Notification> toNotification(CreateNotificationRequest createNotificationRequest) {
        return orderApiClient.getOrder(createNotificationRequest.orderId())
                .onErrorMap(e -> new CreateNotificationException(
                        String.format("Unable to get order id %s", createNotificationRequest.orderId())))
                .flatMap(orderResponse -> customerApiClient.getCustomer(orderResponse.customerId())
                        .onErrorMap(e -> new CreateNotificationException(
                                String.format("Unable to get customer id %s", createNotificationRequest.orderId())))
                        .flatMap(customerResponse -> {
                            Notification notification = new Notification();
                            notification.setOrderId(createNotificationRequest.orderId());
                            notification.setEmail(customerResponse.email());
                            notification.setCreatedAt(LocalDateTime.now());
                            return Mono.just(notification);
                        }));
    }
}
