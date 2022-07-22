package com.ivanfranchin.notificationapi.mapper;

import com.ivanfranchin.notificationapi.client.CustomerApiClient;
import com.ivanfranchin.notificationapi.client.OrderApiClient;
import com.ivanfranchin.notificationapi.exception.CreateNotificationException;
import com.ivanfranchin.notificationapi.model.Notification;
import com.ivanfranchin.notificationapi.rest.dto.CreateNotificationRequest;
import com.ivanfranchin.notificationapi.rest.dto.NotificationResponse;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

    @Autowired
    private OrderApiClient orderApiClient;

    @Autowired
    private CustomerApiClient customerApiClient;

    public abstract NotificationResponse toNotificationResponse(Notification notification);

    public Mono<Notification> toNotification(CreateNotificationRequest createNotificationRequest) {
        return orderApiClient.getOrder(createNotificationRequest.getOrderId())
                .onErrorMap(e -> new CreateNotificationException(
                        String.format("Unable to get order id %s", createNotificationRequest.getOrderId())))
                .flatMap(orderResponse -> customerApiClient.getCustomer(orderResponse.getCustomerId())
                        .onErrorMap(e -> new CreateNotificationException(
                                String.format("Unable to get customer id %s", createNotificationRequest.getOrderId())))
                        .flatMap(customerResponse -> {
                            Notification notification = new Notification();
                            notification.setOrderId(createNotificationRequest.getOrderId());
                            notification.setEmail(customerResponse.getEmail());
                            notification.setCreatedAt(LocalDateTime.now());
                            return Mono.just(notification);
                        }));
    }
}
