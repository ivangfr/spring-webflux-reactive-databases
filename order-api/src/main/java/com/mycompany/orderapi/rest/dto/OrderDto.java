package com.mycompany.orderapi.rest.dto;

import com.mycompany.orderapi.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private OrderStatus status;
    private LocalDateTime created;
    private List<OrderItem> items;
    private String customerId;

    @Data
    public static class OrderItem {

        private String id;
        private Integer quantity;
        private BigDecimal price;

    }

}
