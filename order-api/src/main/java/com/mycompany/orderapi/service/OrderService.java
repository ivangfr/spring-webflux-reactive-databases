package com.mycompany.orderapi.service;

import com.mycompany.orderapi.exception.OrderNotFoundException;
import com.mycompany.orderapi.model.Order;

public interface OrderService {

    Order validateAndGetOrder(Long id) throws OrderNotFoundException;

    Order saveOrder(Order order);

}
