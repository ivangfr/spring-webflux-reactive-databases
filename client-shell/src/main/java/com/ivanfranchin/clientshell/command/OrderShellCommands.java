package com.ivanfranchin.clientshell.command;

import com.ivanfranchin.clientshell.client.OrderApiClient;
import com.ivanfranchin.clientshell.client.ProductApiClient;
import com.ivanfranchin.clientshell.dto.CreateOrderRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderShellCommands {

    private final OrderApiClient orderApiClient;
    private final ProductApiClient productApiClient;
    private final ObjectMapper objectMapper;
    private final Random random;

    public OrderShellCommands(OrderApiClient orderApiClient, ProductApiClient productApiClient, ObjectMapper objectMapper, Random random) {
        this.orderApiClient = orderApiClient;
        this.productApiClient = productApiClient;
        this.objectMapper = objectMapper;
        this.random = random;
    }

    @Command(name = "get-order", description = "Get order by id", group = "Order Commands")
    public String getOrder(@Option(longName = "id", required = true) UUID id) {
        try {
            return orderApiClient.getOrder(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "get-order-detailed", description = "Get order detailed by id", group = "Order Commands")
    public String getOrderDetailed(@Option(longName = "id", required = true) UUID id) {
        try {
            return orderApiClient.getOrderDetailed(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "get-orders", description = "Get all orders")
    public String getOrders() {
        try {
            return Objects.requireNonNull(orderApiClient.getOrders().map(objectMapper::writeValueAsString).collectList().block()).toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "create-order",
            description = "Create order.\n" + "\t\tExample: create-order --customer-id <customer-id> --products <product-1-id:quantity>[;<product-n-id:quantity>]",
            group = "Order Commands")
    public String createOrder(String customerId, Set<CreateOrderRequest.ProductDto> products) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(customerId, products);
        try {
            return orderApiClient.createOrder(createOrderRequest).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "create-order-random", description = "Create order with random products", group = "Order Commands")
    public String createOrderRandom(@Option(longName = "customerId", required = true) String customerId,
                                    @Option(longName = "numProducts", required = true) @Min(1) @Max(50) int numProducts) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(customerId, getProducts(numProducts));
        try {
            return orderApiClient.createOrder(createOrderRequest).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private Set<CreateOrderRequest.ProductDto> getProducts(int numProducts) {
        return productApiClient.getProducts()
                .toStream()
                .limit(numProducts)
                .map(productDto -> new CreateOrderRequest.ProductDto(productDto.id(), getRandomQuantity()))
                .collect(Collectors.toSet());
    }

    private int getRandomQuantity() {
        return 1 + random.nextInt(5);
    }
}
