package com.mycompany.customerapi.rest.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String number;
}
