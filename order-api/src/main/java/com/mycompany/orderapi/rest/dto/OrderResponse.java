package com.mycompany.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponse {

    private UUID orderId;
    private String status;
    private LocalDateTime created;
    private Set<ProductDto> products;
    private String customerId;

    @Data
    @Schema(name = "OrderProductDto")
    public static class ProductDto {
        private String id;
        private Integer quantity;
    }
}
