package com.mycompany.orderapi.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType
public class Product {

    private String id;
    private Integer quantity;
}
