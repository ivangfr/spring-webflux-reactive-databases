package com.ivanfranchin.notificationapi.rest.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class NotificationResponse {

    Long id;
    String orderId;
    String email;
    LocalDateTime createdAt;
}
