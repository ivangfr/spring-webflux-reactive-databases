package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateOrderRequest;
import com.ivanfranchin.clientshell.dto.OrderDetailedResponse;
import com.ivanfranchin.clientshell.dto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@HttpExchange("/api/orders")
public interface OrderApiClient {

    @GetExchange("/{id}")
    Mono<OrderResponse> getOrder(@PathVariable UUID id);

    @GetExchange("/{id}/detailed")
    Mono<OrderDetailedResponse> getOrderDetailed(@PathVariable UUID id);

    @GetExchange
    Flux<OrderResponse> getOrders();

    @PostExchange
    Mono<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest);
}
