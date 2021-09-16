package com.mycompany.clientshell.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateCustomerRequest {

    String name;
    String email;
    String city;
    String street;
    String number;
}
