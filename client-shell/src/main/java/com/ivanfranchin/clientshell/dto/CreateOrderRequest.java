package com.ivanfranchin.clientshell.dto;

import java.util.Set;

public record CreateOrderRequest(String customerId, Set<ProductDto> products) {

    public record ProductDto(String id, Integer quantity) {
    }
}
