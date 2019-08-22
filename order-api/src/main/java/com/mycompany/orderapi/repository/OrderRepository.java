package com.mycompany.orderapi.repository;

import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.OrderKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepository extends ReactiveCassandraRepository<Order, OrderKey> {

    Mono<Order> findByKeyOrderId(UUID id);

}
