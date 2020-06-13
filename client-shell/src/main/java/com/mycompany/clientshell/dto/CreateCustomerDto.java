package com.mycompany.clientshell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerDto {

    private String name;
    private String email;
    private String city;
    private String street;
    private String number;

}
