package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateProductRequest;
import com.ivanfranchin.clientshell.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/products")
public interface ProductApiClient {

    @GetExchange("/{id}")
    Mono<ProductResponse> getProduct(@PathVariable String id);

    @GetExchange
    Flux<ProductResponse> getProducts();

    @PostExchange
    Mono<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest);

    @DeleteExchange("/{id}")
    Mono<ProductResponse> deleteProduct(@PathVariable String id);
}
