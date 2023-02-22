package com.ivanfranchin.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @Schema(example = "Ivan Franchin")
    @NotBlank
    private String name;

    @Schema(example = "ivan.franchin@test.com")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "Berlin")
    @NotBlank
    private String city;

    @Schema(example = "Street New York")
    @NotBlank
    private String street;

    @Schema(example = "10")
    private String number;
}
