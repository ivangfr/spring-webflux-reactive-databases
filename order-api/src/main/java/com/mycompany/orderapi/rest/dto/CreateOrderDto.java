package com.mycompany.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class CreateOrderDto {

    @Schema(example = "1")
    @NotBlank
    private String customerId;

    @NotEmpty
    private Set<ProductDto> products;

    @Data
    @Schema(name = "CreateOrderProductDto")
    public static class ProductDto {

        @Schema(example = "...")
        @NotBlank
        private String id;

        @Schema(example = "1")
        @Positive
        private Integer quantity;

    }

}
