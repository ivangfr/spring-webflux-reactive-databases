package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
public class CreateOrderRequest {

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
