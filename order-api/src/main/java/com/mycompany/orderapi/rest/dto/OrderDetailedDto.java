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
    private Set<Item> items;
    private Customer customer;

    @Data
    public static class Item {

        private String id;
        private String name;
        private Integer quantity;
        private BigDecimal price;

    }

    @Data
    public static class Customer {

        private String id;
        private String name;

    }

}
