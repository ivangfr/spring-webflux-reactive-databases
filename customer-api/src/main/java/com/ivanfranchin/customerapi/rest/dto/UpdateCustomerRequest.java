package com.ivanfranchin.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record UpdateCustomerRequest(
        @Schema(example = "Ivan Franchin 2") String name,
        @Schema(example = "ivan.franchin.2@test.com") @Email String email,
        @Schema(example = "Porto") String city,
        @Schema(example = "Street Los Angeles") String street,
        @Schema(example = "20") String number) {
}
