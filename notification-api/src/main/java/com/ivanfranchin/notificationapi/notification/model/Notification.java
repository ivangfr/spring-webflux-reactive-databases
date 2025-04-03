package com.ivanfranchin.notificationapi.notification.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Notification {

    @Id
    private Long id;

    private String orderId;
    private String email;
    private LocalDateTime createdAt;
}
