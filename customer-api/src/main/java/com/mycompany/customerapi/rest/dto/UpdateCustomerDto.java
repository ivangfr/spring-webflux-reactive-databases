package com.mycompany.customerapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UpdateCustomerDto {

    @ApiModelProperty(example = "Ivan Franchin 2")
    private String name;

    @ApiModelProperty(example = "ivan.franchin.2@test.com", position = 1)
    @Email
    private String email;

    @ApiModelProperty(position = 2)
    private AddressDto address;

    @Data
    public static class AddressDto {

        @ApiModelProperty(example = "Porto")
        private String city;

        @ApiModelProperty(example = "Street Los Angeles", position = 1)
        private String street;

        @ApiModelProperty(example = "20", position = 2)
        private String number;

    }

}
