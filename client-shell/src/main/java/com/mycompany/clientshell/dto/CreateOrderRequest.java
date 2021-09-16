package com.mycompany.clientshell.dto;

import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class CreateOrderRequest {

    String customerId;
    Set<ProductDto> products;

    @Value
    public static class ProductDto {
        String id;
        Integer quantity;
    }
}
