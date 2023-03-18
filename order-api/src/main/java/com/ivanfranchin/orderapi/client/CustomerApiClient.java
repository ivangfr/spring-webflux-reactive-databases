package com.ivanfranchin.orderapi.client;

import com.ivanfranchin.orderapi.client.dto.CustomerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/api/customers")
public interface CustomerApiClient {

    @GetExchange("/{id}")
    Mono<CustomerResponse> getCustomer(@PathVariable String id);
}
