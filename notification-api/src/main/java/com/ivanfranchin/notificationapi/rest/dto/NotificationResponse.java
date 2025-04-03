package com.ivanfranchin.notificationapi.rest.dto;

import com.ivanfranchin.notificationapi.model.Notification;

import java.time.LocalDateTime;

public record NotificationResponse(Long id, String orderId, String email, LocalDateTime createdAt) {

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getOrderId(),
                notification.getEmail(),
                notification.getCreatedAt()
        );
    }
}
