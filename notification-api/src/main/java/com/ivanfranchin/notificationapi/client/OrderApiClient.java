package com.ivanfranchin.notificationapi.client;

import com.ivanfranchin.notificationapi.client.dto.OrderResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/api/orders")
public interface OrderApiClient {

    @GetExchange("/{id}")
    Mono<OrderResponse> getOrder(@PathVariable String id);
}
