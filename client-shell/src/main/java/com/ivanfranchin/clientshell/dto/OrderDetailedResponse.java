package com.ivanfranchin.clientshell.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record OrderDetailedResponse(UUID orderId, String status, String created, Set<ProductDto> products,
                                    CustomerDto customer) {

    public record ProductDto(String id, String name, Integer quantity, BigDecimal price) {
    }

    public record CustomerDto(String id, String name, String email, String city, String street, String number) {
    }
}
