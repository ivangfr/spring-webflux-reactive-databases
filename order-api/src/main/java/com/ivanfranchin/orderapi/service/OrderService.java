package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderService {

    Flux<Order> getOrders();

    Mono<Order> validateAndGetOrder(UUID id);

    Mono<Order> saveOrder(Order order);
}
