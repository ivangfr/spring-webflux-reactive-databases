package com.ivanfranchin.productapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @Schema(example = "iPhone 7") @NotBlank String name,
        @Schema(example = "299.99") @NotNull BigDecimal price) {
}
