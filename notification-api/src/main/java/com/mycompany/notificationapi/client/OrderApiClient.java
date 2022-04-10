package com.mycompany.notificationapi.client;

import com.mycompany.notificationapi.client.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrderApiClient {

    private final WebClient webClient;

    public OrderApiClient(@Qualifier("orderApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<OrderResponse> getOrder(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{orderId}").build(id))
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }
}
