package com.mycompany.clientshell.command;

import com.google.gson.Gson;
import com.mycompany.clientshell.client.OrderApiClient;
import com.mycompany.clientshell.dto.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class OrderShellCommands {

    private final OrderApiClient orderApiClient;
    private final Gson gson;

    @ShellMethod("Get order by id")
    public String getOrder(UUID id) {
        return orderApiClient.getOrder(id).map(gson::toJson).block();
    }

    @ShellMethod("Get detailed order by id")
    public String getDetailedOrder(UUID id) {
        return orderApiClient.getDetailedOrder(id).map(gson::toJson).block();
    }

    @ShellMethod("Get all orders")
    public List<String> getOrders() {
        return orderApiClient.getOrders().map(gson::toJson).collectList().block();
    }

    @ShellMethod("Create order")
    public String createOrder(String customerId, Set<CreateOrderDto.ProductDto> products) {
        return orderApiClient.createOrder(customerId, products).map(gson::toJson).block();
    }

}
