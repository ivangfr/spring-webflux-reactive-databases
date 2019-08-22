package com.mycompany.orderapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateOrderDto {

    @ApiModelProperty(example = "1")
    @NotBlank
    private String customerId;

    @ApiModelProperty(position = 1)
    @NotEmpty
    private Set<Item> items;

    @Data
    public static class Item {

        @ApiModelProperty(example = "123")
        @NotBlank
        private String id;

        @ApiModelProperty(example = "1")
        @Positive
        private Integer quantity;

        @ApiModelProperty(example = "199.99")
        @Positive
        private BigDecimal price;

    }

}
