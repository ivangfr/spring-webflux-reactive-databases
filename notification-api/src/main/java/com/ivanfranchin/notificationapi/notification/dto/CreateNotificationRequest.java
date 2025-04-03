package com.ivanfranchin.notificationapi.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateNotificationRequest(@Schema(example = "...") @NotBlank String orderId) {
}
