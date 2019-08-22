package com.mycompany.orderapi.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.math.BigDecimal;

@Data
@UserDefinedType
public class Item {

    private String id;
    private Integer quantity;
    private BigDecimal price;

}
