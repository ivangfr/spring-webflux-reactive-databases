package com.mycompany.clientshell.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
public class OrderDetailedResponse {

    private UUID orderId;
    private String status;
    private String created;
    private Set<ProductDto> products;
    private CustomerDto customer;

    @Getter
    public static class ProductDto {
        private String id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
    }

    @Getter
    public static class CustomerDto {
        private String id;
        private String name;
        private String email;
        private String city;
        private String street;
        private String number;
    }
}
