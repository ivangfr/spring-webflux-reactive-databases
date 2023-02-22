package com.ivanfranchin.notificationapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNotificationRequest {

    @Schema(example = "...")
    @NotBlank
    private String orderId;
}
