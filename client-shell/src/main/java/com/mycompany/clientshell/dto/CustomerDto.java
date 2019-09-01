package com.mycompany.clientshell.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String id;
    private String name;
    private String email;
    private AddressDto address;

    @Data
    public static class AddressDto {

        private String city;
        private String street;
        private String number;

    }

}
