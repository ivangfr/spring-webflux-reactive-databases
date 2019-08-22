package com.mycompany.productapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductDto {

    @ApiModelProperty(example = "iPhone 7")
    @NotBlank
    private String name;

    @ApiModelProperty(example = "299.99", position = 1)
    @NotNull
    private BigDecimal price;

}
