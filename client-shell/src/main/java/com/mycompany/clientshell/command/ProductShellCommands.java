package com.mycompany.clientshell.command;

import com.google.gson.Gson;
import com.mycompany.clientshell.client.ProductApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class ProductShellCommands {

    private final ProductApiClient productApiClient;
    private final Gson gson;

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
        return productApiClient.createProduct(name, price).map(gson::toJson).block();
    }

    @ShellMethod("Delete product")
    public String deleteProduct(String id) {
        return productApiClient.deleteProduct(id).map(gson::toJson).block();
    }

}
