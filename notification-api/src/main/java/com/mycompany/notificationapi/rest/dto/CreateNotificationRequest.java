package com.mycompany.notificationapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateNotificationRequest {

    @Schema(example = "123")
    @NotBlank
    private String orderId;
}
