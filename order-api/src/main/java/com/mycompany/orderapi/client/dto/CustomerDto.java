package com.mycompany.orderapi.client.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String number;

}
