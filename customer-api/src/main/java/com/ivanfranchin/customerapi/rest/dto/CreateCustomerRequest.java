package com.ivanfranchin.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @Schema(example = "Ivan Franchin") @NotBlank String name,
        @Schema(example = "ivan.franchin@test.com") @Email @NotBlank String email,
        @Schema(example = "Berlin") @NotBlank String city,
        @Schema(example = "Street New York") @NotBlank String street,
        @Schema(example = "10") String number) {
}
