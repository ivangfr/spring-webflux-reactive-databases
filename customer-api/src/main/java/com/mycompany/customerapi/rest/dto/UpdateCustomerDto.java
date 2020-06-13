package com.mycompany.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UpdateCustomerDto {

    @Schema(example = "Ivan Franchin 2")
    private String name;

    @Schema(example = "ivan.franchin.2@test.com")
    @Email
    private String email;

    @Schema(example = "Porto")
    private String city;

    @Schema(example = "Street Los Angeles")
    private String street;

    @Schema(example = "20")
    private String number;

}
