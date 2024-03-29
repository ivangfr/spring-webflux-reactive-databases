package com.ivanfranchin.clientshell.command;

import com.google.gson.Gson;
import com.ivanfranchin.clientshell.client.CustomerApiClient;
import com.ivanfranchin.clientshell.dto.CreateCustomerRequest;
import jakarta.validation.constraints.Email;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class CustomerShellCommands {

    private final CustomerApiClient customerApiClient;
    private final Gson gson;

    public CustomerShellCommands(CustomerApiClient customerApiClient, Gson gson) {
        this.customerApiClient = customerApiClient;
        this.gson = gson;
    }

    @ShellMethod("Get customer by id")
    public String getCustomer(String id) {
        return customerApiClient.getCustomer(id).map(gson::toJson).block();
    }

    @ShellMethod("Get all customers")
    public List<String> getCustomers() {
        return customerApiClient.getCustomers().map(gson::toJson).collectList().block();
    }

    @ShellMethod("Create customer")
    public String createCustomer(String name, @Email String email, String city, String street, String number) {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(name, email, city, street, number);
        return customerApiClient.createCustomer(createCustomerRequest).map(gson::toJson).block();
    }

    @ShellMethod("Delete customer")
    public String deleteCustomer(String id) {
        return customerApiClient.deleteCustomer(id).map(gson::toJson).block();
    }
}
