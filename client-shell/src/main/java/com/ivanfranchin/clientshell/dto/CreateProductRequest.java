package com.ivanfranchin.clientshell.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class CreateProductRequest {

    String name;
    BigDecimal price;
}
