package com.ivanfranchin.orderapi.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record CreateOrderRequest(
        @Schema(example = "1") @NotBlank String customerId,
        @NotEmpty Set<ProductDto> products) {

    @Schema(name = "CreateOrderProductDto")
    public record ProductDto(
            @Schema(example = "...") @NotBlank String id,
            @Schema(example = "1") @Positive Integer quantity) {
    }
}
