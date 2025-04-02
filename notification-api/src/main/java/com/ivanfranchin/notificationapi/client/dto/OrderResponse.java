package com.ivanfranchin.notificationapi.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record OrderResponse(UUID orderId, String status, LocalDateTime created, Set<ProductDto> products,
                            String customerId) {

    @Schema(name = "OrderProductDto")
    public record ProductDto(String id, Integer quantity) {
    }
}
