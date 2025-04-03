package com.ivanfranchin.customerapi.model;

import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {

    @Id
    private Long id;

    private String name;
    private String email;
    private String city;
    private String street;
    private String number;

    public static Customer from(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setName(createCustomerRequest.name());
        customer.setEmail(createCustomerRequest.email());
        customer.setCity(createCustomerRequest.city());
        customer.setStreet(createCustomerRequest.street());
        customer.setNumber(createCustomerRequest.number());
        return customer;
    }

    public static void updateFrom(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if (updateCustomerRequest.name() != null) {
            customer.setName(updateCustomerRequest.name());
        }
        if (updateCustomerRequest.email() != null) {
            customer.setEmail(updateCustomerRequest.email());
        }
        if (updateCustomerRequest.city() != null) {
            customer.setCity(updateCustomerRequest.city());
        }
        if (updateCustomerRequest.street() != null) {
            customer.setStreet(updateCustomerRequest.street());
        }
        if (updateCustomerRequest.number() != null) {
            customer.setNumber(updateCustomerRequest.number());
        }
    }
}
