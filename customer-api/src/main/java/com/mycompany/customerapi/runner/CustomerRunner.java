package com.mycompany.customerapi.runner;

import com.mycompany.customerapi.model.Address;
import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomerRunner implements CommandLineRunner {

    @Value("${runner.num-customers}")
    private int numCustomers;

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        Flux<Customer> customers = Flux.range(1, numCustomers)
                .map(i -> {
                    Customer customer = new Customer();
                    customer.setId(UUID.randomUUID().toString());
                    customer.setName(String.format("Customer-%s", i));
                    customer.setEmail(String.format("customer.%s@test.com", i));
                    Address address = new Address();
                    address.setCity(String.format("Customer-%s-city", i));
                    address.setStreet(String.format("Customer-%s-street", i));
                    address.setNumber(String.format("Customer-%s-number", i));
                    customer.setAddress(address);
                    return customer;
                }).flatMap(customerRepository::save);

        customerRepository.saveAll(customers)
                .thenMany(customerRepository.findAll())
                .subscribe(log::info);
    }

}
