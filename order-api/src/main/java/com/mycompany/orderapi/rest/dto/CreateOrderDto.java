package com.mycompany.orderapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class CreateOrderDto {

    @ApiModelProperty(example = "...")
    @NotBlank
    private String customerId;

    @ApiModelProperty(position = 1)
    @NotEmpty
    private Set<ProductDto> products;

    @Data
    public static class ProductDto {

        @ApiModelProperty(example = "...")
        @NotBlank
        private String id;

        @ApiModelProperty(example = "1")
        @Positive
        private Integer quantity;

    }

}
