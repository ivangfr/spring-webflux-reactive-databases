package com.ivanfranchin.orderapi.order;

import com.ivanfranchin.orderapi.order.model.Order;
import com.ivanfranchin.orderapi.order.model.OrderKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCassandraRepository<Order, OrderKey> {

    Mono<Order> findByKeyOrderId(UUID id);
}
