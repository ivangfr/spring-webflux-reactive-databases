package com.mycompany.orderapi.rest.dto;

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

    @Data
    @Schema(name = "OrderDetailedProductDto")
    public static class ProductDto {
        private String id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
    }

    @Data
    public static class CustomerDto {
        private String id;
        private String name;
        private String email;
        private String city;
        private String street;
        private String number;
    }
}
