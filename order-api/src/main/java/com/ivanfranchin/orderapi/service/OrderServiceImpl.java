package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.Order;
import com.ivanfranchin.orderapi.repository.OrderRepository;
import com.ivanfranchin.orderapi.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Flux<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Mono<Order> validateAndGetOrder(UUID id) {
        return orderRepository.findByKeyOrderId(id).switchIfEmpty(Mono.error(new OrderNotFoundException(id)));
    }

    @Override
    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
