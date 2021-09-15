package com.mycompany.orderapi.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;

@Data
@Table("orders")
public class Order {

    @PrimaryKey
    private OrderKey key;

    private Status status = Status.OPEN;
    private Set<Product> products;
    private String customerId;

    public enum Status {
        OPEN, CANCELLED, DELIVERED
    }
}
