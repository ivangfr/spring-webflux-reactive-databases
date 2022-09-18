package com.ivanfranchin.clientshell.dto;

import java.util.Set;
import java.util.UUID;

public record OrderResponse(UUID orderId, String status, String created, Set<ProductDto> products, String customerId) {

    public record ProductDto(String id, Integer quantity) {
    }
}
