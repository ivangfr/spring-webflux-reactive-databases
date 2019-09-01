package com.mycompany.clientshell.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID orderId;
    private Status status;
    private String created;
    private Set<ProductDto> products;
    private String customerId;

    public enum Status {
        OPEN, CANCELLED, DELIVERED
    }

    @Data
    public static class ProductDto {

        private String id;
        private Integer quantity;

    }

}
