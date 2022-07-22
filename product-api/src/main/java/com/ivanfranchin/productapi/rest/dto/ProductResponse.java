package com.ivanfranchin.productapi.rest.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductResponse {

    String id;
    String name;
    BigDecimal price;
}
