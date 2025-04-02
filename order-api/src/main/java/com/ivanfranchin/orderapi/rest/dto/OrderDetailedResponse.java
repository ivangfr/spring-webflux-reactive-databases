package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDetailedResponse {

    private UUID orderId;
    private String status;
    private LocalDateTime created;
    private Set<ProductDto> products;
    private CustomerDto customer;

    @Schema(name = "OrderDetailedProductDto")
    public record ProductDto(String id, String name, Integer quantity, BigDecimal price) {
    }

    public record CustomerDto(String id, String name, String email, String city, String street, String number) {
    }
}
