package com.mycompany.customerapi.rest;

import com.mycompany.customerapi.mapper.CustomerMapper;
import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerDto;
import com.mycompany.customerapi.rest.dto.CustomerDto;
import com.mycompany.customerapi.rest.dto.UpdateCustomerDto;
import com.mycompany.customerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public Flux<CustomerDto> getCustomers() {
        log.info("==> getCustomers");
        return customerService.getCustomers().map(customerMapper::toCustomerDto);
    }

    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable Long id) {
        log.info("==> getCustomer {}", id);
        return customerService.validateAndGetCustomer(id)/*.map(customerMapper::toCustomerDto)*/;
    }

    @PostMapping
    public Mono<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        log.info("==> createCustomer {} ", createCustomerDto);
        Customer customer = customerMapper.toCustomer(createCustomerDto);
        return customerService.saveCustomer(customer).map(customerMapper::toCustomerDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
        log.info("==> updateCustomer {} with {}", id, updateCustomerDto);
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> {
                    customerMapper.updateCustomerFromDto(updateCustomerDto, customer);
                    customerService.saveCustomer(customer).subscribe();
                })
                .map(customerMapper::toCustomerDto);
    }

    @DeleteMapping("/{id}")
    public Mono<CustomerDto> deleteCustomer(@PathVariable Long id) {
        log.info("==> deleteCustomer {}", id);
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> customerService.deleteCustomer(customer).subscribe())
                .map(customerMapper::toCustomerDto);
    }

}
