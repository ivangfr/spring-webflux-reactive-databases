package com.ivanfranchin.orderapi.client.dto;

import lombok.Data;

@Data
public class CustomerResponse {

    private String id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String number;
}
