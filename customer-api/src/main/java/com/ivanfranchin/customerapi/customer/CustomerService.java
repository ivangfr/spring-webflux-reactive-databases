package com.ivanfranchin.customerapi.customer;

import com.ivanfranchin.customerapi.customer.exception.CustomerNotFoundException;
import com.ivanfranchin.customerapi.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Mono<Customer> validateAndGetCustomer(Long id) {
        return customerRepository.findById(id).switchIfEmpty(Mono.error(new CustomerNotFoundException(id)));
    }

    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Void> deleteCustomer(Customer customer) {
        return customerRepository.delete(customer);
    }
}
