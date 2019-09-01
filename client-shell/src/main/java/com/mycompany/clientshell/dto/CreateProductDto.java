package com.mycompany.clientshell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateProductDto {

    private String name;
    private BigDecimal price;

}
