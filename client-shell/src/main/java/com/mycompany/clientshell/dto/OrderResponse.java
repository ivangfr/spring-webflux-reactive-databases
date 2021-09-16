package com.mycompany.clientshell.dto;

import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class OrderResponse {

    private UUID orderId;
    private String status;
    private String created;
    private Set<ProductDto> products;
    private String customerId;

    @Getter
    public static class ProductDto {
        private String id;
        private Integer quantity;
    }
}
