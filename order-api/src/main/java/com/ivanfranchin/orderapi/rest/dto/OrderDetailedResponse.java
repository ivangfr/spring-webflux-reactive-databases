package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record OrderDetailedResponse(UUID orderId, String status, LocalDateTime created, Set<ProductDto> products,
                                    CustomerDto customer) {

    @Schema(name = "OrderDetailedProductDto")
    public record ProductDto(String id, String name, Integer quantity, BigDecimal price) {
    }

    public record CustomerDto(String id, String name, String email, String city, String street, String number) {
    }
}
