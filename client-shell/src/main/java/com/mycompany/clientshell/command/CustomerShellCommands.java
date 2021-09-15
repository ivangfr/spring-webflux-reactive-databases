package com.mycompany.clientshell.command;

import com.google.gson.Gson;
import com.mycompany.clientshell.client.CustomerApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.validation.constraints.Email;
import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class CustomerShellCommands {

    private final CustomerApiClient customerApiClient;
    private final Gson gson;

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
        return customerApiClient.createCustomer(name, email, city, street, number).map(gson::toJson).block();
    }

    @ShellMethod("Delete customer")
    public String deleteCustomer(String id) {
        return customerApiClient.deleteCustomer(id).map(gson::toJson).block();
    }
}
