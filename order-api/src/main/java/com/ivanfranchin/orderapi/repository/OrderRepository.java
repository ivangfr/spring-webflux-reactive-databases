package com.ivanfranchin.orderapi.repository;

import com.ivanfranchin.orderapi.model.Order;
import com.ivanfranchin.orderapi.model.OrderKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCassandraRepository<Order, OrderKey> {

    Mono<Order> findByKeyOrderId(UUID id);
}
