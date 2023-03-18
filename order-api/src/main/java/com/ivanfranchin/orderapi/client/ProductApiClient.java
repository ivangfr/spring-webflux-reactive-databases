package com.ivanfranchin.orderapi.client;

import com.ivanfranchin.orderapi.client.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("/api/products")
public interface ProductApiClient {

    @GetExchange("/{id}")
    Mono<ProductResponse> getProduct(@PathVariable String id);
}
