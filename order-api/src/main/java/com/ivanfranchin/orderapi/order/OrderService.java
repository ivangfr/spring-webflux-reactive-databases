package com.ivanfranchin.orderapi.order;

import com.ivanfranchin.orderapi.order.exception.OrderNotFoundException;
import com.ivanfranchin.orderapi.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Flux<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> validateAndGetOrder(UUID id) {
        return orderRepository.findByKeyOrderId(id).switchIfEmpty(Mono.error(new OrderNotFoundException(id)));
    }

    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
