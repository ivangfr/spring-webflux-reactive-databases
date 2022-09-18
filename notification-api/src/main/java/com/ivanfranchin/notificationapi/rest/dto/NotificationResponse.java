package com.ivanfranchin.notificationapi.rest.dto;

import java.time.LocalDateTime;

public record NotificationResponse(Long id, String orderId, String email, LocalDateTime createdAt) {
}
