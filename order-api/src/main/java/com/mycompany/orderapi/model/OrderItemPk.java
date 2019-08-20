package com.mycompany.orderapi.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemPk implements Serializable {

    private String id;
    private Order order;

}
