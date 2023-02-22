package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @Schema(example = "iPhone 7")
    @NotBlank
    private String name;

    @Schema(example = "299.99")
    @NotNull
    private BigDecimal price;
}
