package com.mycompany.customerapi.rest;

import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerDto;
import com.mycompany.customerapi.rest.dto.CustomerDto;
import com.mycompany.customerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final MapperFacade mapperFacade;

    @GetMapping
    public Flux<CustomerDto> getCustomers() {
        return customerService.getCustomers()
                .map(c -> mapperFacade.map(c, CustomerDto.class));
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable String id) {
        return customerService.validateAndGetCustomer(id)
                .map(c -> mapperFacade.map(c, CustomerDto.class));
    }

    @PostMapping
    public Mono<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = mapperFacade.map(createCustomerDto, Customer.class);
        customer.setId(UUID.randomUUID().toString());
        return customerService.saveCustomer(customer)
                .map(c -> mapperFacade.map(c, CustomerDto.class));
    }

}
