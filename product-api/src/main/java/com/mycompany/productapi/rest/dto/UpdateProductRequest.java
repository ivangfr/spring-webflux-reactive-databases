package com.mycompany.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @Schema(example = "iPhone 8")
    private String name;

    @Schema(example = "399.99")
    private BigDecimal price;
}
