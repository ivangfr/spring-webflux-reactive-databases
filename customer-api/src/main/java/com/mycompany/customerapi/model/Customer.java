package com.mycompany.customerapi.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

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

}
