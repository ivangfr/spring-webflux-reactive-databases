package com.mycompany.orderapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class AddOrderItemDto {

    @ApiModelProperty(example = "1")
    @NotBlank
    private String id;

    @ApiModelProperty(example = "1", position = 1)
    @Positive
    private Integer quantity;

    @ApiModelProperty(example = "199.99", position = 2)
    @NotNull
    private BigDecimal price;

}
