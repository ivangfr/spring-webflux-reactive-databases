package com.mycompany.clientshell.client;

import com.mycompany.clientshell.dto.CreateOrderRequest;
import com.mycompany.clientshell.dto.OrderDetailedResponse;
import com.mycompany.clientshell.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Component
public class OrderApiClient {

    private final WebClient webClient;

    public OrderApiClient(@Qualifier("orderApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<OrderResponse> getOrder(UUID id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    public Mono<OrderDetailedResponse> getOrderDetailed(UUID id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}/detailed").build(id))
                .retrieve()
                .bodyToMono(OrderDetailedResponse.class);
    }

    public Flux<OrderResponse> getOrders() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    public Mono<OrderResponse> createOrder(String customerId, Set<CreateOrderRequest.ProductDto> products) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CreateOrderRequest.of(customerId, products))
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }
}
