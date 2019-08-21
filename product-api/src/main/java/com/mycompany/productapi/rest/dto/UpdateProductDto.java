package com.mycompany.productapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDto {

    @ApiModelProperty(example = "iPhone 8")
    private String name;

    @ApiModelProperty(position = 1, example = "399.99")
    private BigDecimal price;

}
