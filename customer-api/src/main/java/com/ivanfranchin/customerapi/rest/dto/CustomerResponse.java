package com.ivanfranchin.customerapi.rest.dto;

import lombok.Value;

@Value
public class CustomerResponse {

    Long id;
    String name;
    String email;
    String city;
    String street;
    String number;
}
