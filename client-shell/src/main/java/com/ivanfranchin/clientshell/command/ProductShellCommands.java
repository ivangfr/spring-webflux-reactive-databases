package com.ivanfranchin.clientshell.command;

import com.ivanfranchin.clientshell.client.ProductApiClient;
import com.ivanfranchin.clientshell.dto.CreateProductRequest;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ProductShellCommands {

    private final ProductApiClient productApiClient;
    private final ObjectMapper objectMapper;

    public ProductShellCommands(ProductApiClient productApiClient, ObjectMapper objectMapper) {
        this.productApiClient = productApiClient;
        this.objectMapper = objectMapper;
    }

    @Command(name = "get-product", description = "Get product by id", group = "Product Commands")
    public String getProduct(@Option(longName = "id", required = true) String id) {
        try {
            return productApiClient.getProduct(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "get-products", description = "Get all products", group = "Product Commands")
    public String getProducts() {
        try {
            return Objects.requireNonNull(productApiClient.getProducts().map(objectMapper::writeValueAsString).collectList().block()).toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "create-product", description = "Create product", group = "Product Commands")
    public String createProduct(@Option(longName = "name", required = true) String name,
                                @Option(longName = "price", required = true) BigDecimal price) {
        CreateProductRequest createProductRequest = new CreateProductRequest(name, price);
        try {
            return productApiClient.createProduct(createProductRequest).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "delete-product", description = "Delete product", group = "Product Commands")
    public String deleteProduct(@Option(longName = "id", required = true) String id) {
        try {
            return productApiClient.deleteProduct(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
