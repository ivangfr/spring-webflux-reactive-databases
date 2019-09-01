package com.mycompany.clientshell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerDto {

    private String name;
    private String email;
    private AddressDto address;

    @Data
    @AllArgsConstructor
    public static class AddressDto {

        private String city;
        private String street;
        private String number;

    }

}
