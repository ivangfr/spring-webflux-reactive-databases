package com.ivanfranchin.productapi.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @Schema(example = "iPhone 8") String name,
        @Schema(example = "399.99") BigDecimal price) {
}
