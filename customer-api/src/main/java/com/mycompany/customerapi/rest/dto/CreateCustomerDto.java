package com.mycompany.customerapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCustomerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

}
