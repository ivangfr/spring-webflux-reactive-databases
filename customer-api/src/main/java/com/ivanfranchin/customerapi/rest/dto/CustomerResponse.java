package com.ivanfranchin.customerapi.rest.dto;

import com.ivanfranchin.customerapi.model.Customer;

public record CustomerResponse(Long id, String name, String email, String city, String street, String number) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCity(),
                customer.getStreet(),
                customer.getNumber()
        );
    }
}
