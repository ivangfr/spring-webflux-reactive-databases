package com.ivanfranchin.clientshell.command;

import com.google.gson.Gson;
import com.ivanfranchin.clientshell.client.ProductApiClient;
import com.ivanfranchin.clientshell.dto.CreateProductRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.math.BigDecimal;
import java.util.List;

@ShellComponent
public class ProductShellCommands {

    private final ProductApiClient productApiClient;
    private final Gson gson;

    public ProductShellCommands(ProductApiClient productApiClient, Gson gson) {
        this.productApiClient = productApiClient;
        this.gson = gson;
    }

    @ShellMethod("Get product by id")
    public String getProduct(String id) {
        return productApiClient.getProduct(id).map(gson::toJson).block();
    }

    @ShellMethod("Get all products")
    public List<String> getProducts() {
        return productApiClient.getProducts().map(gson::toJson).collectList().block();
    }

    @ShellMethod("Create product")
    public String createProduct(String name, BigDecimal price) {
        CreateProductRequest createProductRequest = new CreateProductRequest(name, price);
        return productApiClient.createProduct(createProductRequest).map(gson::toJson).block();
    }

    @ShellMethod("Delete product")
    public String deleteProduct(String id) {
        return productApiClient.deleteProduct(id).map(gson::toJson).block();
    }
}
