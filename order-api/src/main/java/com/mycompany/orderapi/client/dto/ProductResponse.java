package com.mycompany.orderapi.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private String id;
    private String name;
    private BigDecimal price;
}
