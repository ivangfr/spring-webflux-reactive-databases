package com.mycompany.orderapi.rest.dto;

import com.mycompany.orderapi.model.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDetailedDto {

    private UUID orderId;
    private Order.Status status;
    private LocalDateTime created;
    private Set<ItemDto> items;
    private CustomerDto customer;

    @Data
    public static class ItemDto {

        private String id;
        private String name;
        private Integer quantity;
        private BigDecimal price;

    }

    @Data
    public static class CustomerDto {

        private String id;
        private String name;

    }

}
