package com.ivanfranchin.customerapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {

    @Id
    private Long id;

    private String name;
    private String email;
    private String city;
    private String street;
    private String number;
}
