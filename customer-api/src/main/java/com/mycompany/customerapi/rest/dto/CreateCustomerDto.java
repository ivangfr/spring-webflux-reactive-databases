package com.mycompany.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateCustomerDto {

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
