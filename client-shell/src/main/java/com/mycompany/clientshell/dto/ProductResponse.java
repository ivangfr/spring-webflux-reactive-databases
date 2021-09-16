package com.mycompany.clientshell.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponse {

    private String id;
    private String name;
    private BigDecimal price;
}
