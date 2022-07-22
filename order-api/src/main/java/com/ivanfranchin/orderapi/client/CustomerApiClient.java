package com.ivanfranchin.orderapi.client;

import com.ivanfranchin.orderapi.client.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    private final WebClient webClient;

    public CustomerApiClient(@Qualifier("customerApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CustomerResponse> getCustomer(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{customerId}").build(id))
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }
}
