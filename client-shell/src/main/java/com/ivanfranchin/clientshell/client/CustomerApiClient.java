package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateCustomerRequest;
import com.ivanfranchin.clientshell.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    private final WebClient webClient;

    public CustomerApiClient(@Qualifier("customerApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CustomerResponse> getCustomer(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }

    public Flux<CustomerResponse> getCustomers() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(CustomerResponse.class);
    }

    public Mono<CustomerResponse> createCustomer(String name, String email, String city, String street, String number) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateCustomerRequest(name, email, city, street, number))
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }

    public Mono<CustomerResponse> deleteCustomer(String id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(CustomerResponse.class);
    }
}
