package com.mycompany.customerapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Data
@Document
public class Customer {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String email;

    @Field
    private Address address;

    @Data
    public static class Address {

        private String city;
        private String street;
        private String number;

    }
}
