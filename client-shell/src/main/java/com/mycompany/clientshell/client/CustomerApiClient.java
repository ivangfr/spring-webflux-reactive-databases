package com.mycompany.clientshell.client;

import com.mycompany.clientshell.dto.CreateCustomerDto;
import com.mycompany.clientshell.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerApiClient {

    @Autowired
    @Qualifier("customerApiWebClient")
    private WebClient webClient;

    public Mono<CustomerDto> getCustomer(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(CustomerDto.class);
    }

    public Flux<CustomerDto> getCustomers() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(CustomerDto.class);
    }

    public Mono<CustomerDto> createCustomer(String name, String email, String city, String street, String number) {
        CreateCustomerDto.AddressDto addressDto = new CreateCustomerDto.AddressDto(city, street, number);
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(name, email, addressDto);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createCustomerDto)
                .retrieve()
                .bodyToMono(CustomerDto.class);
    }

    public Mono<CustomerDto> deleteCustomer(String id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(CustomerDto.class);
    }

}
