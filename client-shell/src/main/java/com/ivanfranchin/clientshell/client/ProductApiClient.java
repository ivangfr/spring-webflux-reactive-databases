package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateProductRequest;
import com.ivanfranchin.clientshell.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class ProductApiClient {

    private final WebClient webClient;

    public ProductApiClient(@Qualifier("productApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ProductResponse> getProduct(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    public Flux<ProductResponse> getProducts() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(ProductResponse.class);

    }

    public Mono<ProductResponse> createProduct(String name, BigDecimal price) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateProductRequest(name, price))
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    public Mono<ProductResponse> deleteProduct(String id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }
}
