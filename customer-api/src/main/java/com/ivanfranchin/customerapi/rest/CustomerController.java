package com.ivanfranchin.customerapi.rest;

import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.mapper.CustomerMapper;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import com.ivanfranchin.customerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CustomerResponse> getCustomers() {
        return customerService.getCustomers().map(customerMapper::toCustomerResponse);
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponse> getCustomer(@PathVariable Long id) {
        return customerService.validateAndGetCustomer(id).map(customerMapper::toCustomerResponse);
    }

    @PostMapping
    public Mono<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        return customerService.saveCustomer(customer).map(customerMapper::toCustomerResponse);
    }

    @PatchMapping("/{id}")
    public Mono<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                 @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> {
                    customerMapper.updateCustomerFromRequest(updateCustomerRequest, customer);
                    customerService.saveCustomer(customer).subscribe();
                })
                .map(customerMapper::toCustomerResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<CustomerResponse> deleteCustomer(@PathVariable Long id) {
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> customerService.deleteCustomer(customer).subscribe())
                .map(customerMapper::toCustomerResponse);
    }
}
