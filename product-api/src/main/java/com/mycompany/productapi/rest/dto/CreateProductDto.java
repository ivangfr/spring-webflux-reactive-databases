package com.mycompany.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductDto {

    @Schema(example = "iPhone 7")
    @NotBlank
    private String name;

    @Schema(example = "299.99")
    @NotNull
    private BigDecimal price;
}
