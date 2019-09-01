package com.mycompany.clientshell.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private String id;
    private String name;
    private BigDecimal price;

}
