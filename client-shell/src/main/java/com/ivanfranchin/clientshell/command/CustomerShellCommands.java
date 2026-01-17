package com.ivanfranchin.clientshell.command;

import com.ivanfranchin.clientshell.client.CustomerApiClient;
import com.ivanfranchin.clientshell.dto.CreateCustomerRequest;
import jakarta.validation.constraints.Email;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;

@Component
public class CustomerShellCommands {

    private final CustomerApiClient customerApiClient;
    private final ObjectMapper objectMapper;

    public CustomerShellCommands(CustomerApiClient customerApiClient, ObjectMapper objectMapper) {
        this.customerApiClient = customerApiClient;
        this.objectMapper = objectMapper;
    }

    @Command(name = "get-customer", description = "Get customer by id", group = "Customer Commands")
    public String getCustomer(@Option(longName = "id", required = true) String id) {
        try {
            return customerApiClient.getCustomer(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "get-customers", description = "Get all customers", group = "Customer Commands")
    public String getCustomers() {
        try {
            return Objects.requireNonNull(customerApiClient.getCustomers().map(objectMapper::writeValueAsString).collectList().block()).toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "create-customer", description = "Create customer", group = "Customer Commands")
    public String createCustomer(@Option(longName = "name", required = true) String name,
                                 @Option(longName = "email", required = true) @Email String email,
                                 @Option(longName = "city", required = true) String city,
                                 @Option(longName = "street", required = true) String street,
                                 @Option(longName = "number", required = true) String number) {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(name, email, city, street, number);
        try {
            return customerApiClient.createCustomer(createCustomerRequest).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "delete-customer", description = "Delete customer", group = "Customer Commands")
    public String deleteCustomer(@Option(longName = "id", required = true) String id) {
        try {
            return customerApiClient.deleteCustomer(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
