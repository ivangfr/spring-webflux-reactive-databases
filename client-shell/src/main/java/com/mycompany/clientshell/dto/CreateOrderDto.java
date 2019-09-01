package com.mycompany.clientshell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CreateOrderDto {

    private String customerId;
    private Set<ProductDto> products;

    @Data
    @AllArgsConstructor
    public static class ProductDto {

        private String id;
        private Integer quantity;

    }

}
