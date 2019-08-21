package com.mycompany.customerapi.service;

import com.mycompany.customerapi.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> validateAndGetCustomer(String id);

    Flux<Customer> getCustomers();

    Mono<Customer> saveCustomer(Customer customer);

}
