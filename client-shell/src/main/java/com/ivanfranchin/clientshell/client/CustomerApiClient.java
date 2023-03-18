package com.ivanfranchin.clientshell.client;

import com.ivanfranchin.clientshell.dto.CreateCustomerRequest;
import com.ivanfranchin.clientshell.dto.CustomerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/customers")
public interface CustomerApiClient {

    @GetExchange("/{id}")
    Mono<CustomerResponse> getCustomer(@PathVariable String id);

    @GetExchange
    Flux<CustomerResponse> getCustomers();

    @PostExchange
    Mono<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest);

    @DeleteExchange("/{id}")
    Mono<CustomerResponse> deleteCustomer(@PathVariable String id);
}
