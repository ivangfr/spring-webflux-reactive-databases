package com.mycompany.orderapi.client;

import com.mycompany.orderapi.client.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    @Autowired
    @Qualifier("customerApiWebClient")
    private WebClient webClient;

    public Mono<CustomerResponse> getCustomer(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{customerId}").build(id))
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }
}
