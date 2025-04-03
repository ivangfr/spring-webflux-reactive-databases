package com.ivanfranchin.customerapi.rest;

import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import com.ivanfranchin.customerapi.service.CustomerService;
import jakarta.validation.Valid;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CustomerResponse> getCustomers() {
        return customerService.getCustomers().map(CustomerResponse::from);
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponse> getCustomer(@PathVariable Long id) {
        return customerService.validateAndGetCustomer(id).map(CustomerResponse::from);
    }

    @PostMapping
    public Mono<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = Customer.from(createCustomerRequest);
        return customerService.saveCustomer(customer).map(CustomerResponse::from);
    }

    @PatchMapping("/{id}")
    public Mono<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                 @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> {
                    Customer.updateFrom(updateCustomerRequest, customer);
                    customerService.saveCustomer(customer).subscribe();
                })
                .map(CustomerResponse::from);
    }

    @DeleteMapping("/{id}")
    public Mono<CustomerResponse> deleteCustomer(@PathVariable Long id) {
        return customerService.validateAndGetCustomer(id)
                .doOnSuccess(customer -> customerService.deleteCustomer(customer).subscribe())
                .map(CustomerResponse::from);
    }
}
