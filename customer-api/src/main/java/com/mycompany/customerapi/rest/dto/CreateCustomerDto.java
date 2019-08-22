package com.mycompany.customerapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCustomerDto {

    @ApiModelProperty(example = "Ivan Franchin")
    @NotBlank
    private String name;

    @ApiModelProperty(example = "ivan.franchin@test.com", position = 1)
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty(position = 2)
    @NotNull
    private AddressDto address;

    @Data
    public static class AddressDto {

        @ApiModelProperty(example = "Berlin")
        @NotBlank
        private String city;

        @ApiModelProperty(example = "Street New York", position = 1)
        @NotBlank
        private String street;

        @ApiModelProperty(example = "10", position = 2)
        private String number;

    }

}
