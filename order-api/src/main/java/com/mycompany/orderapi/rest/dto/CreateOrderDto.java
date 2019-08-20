package com.mycompany.orderapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderDto {

    @ApiModelProperty(example = "1")
    @NotBlank
    private String customerId;

}
