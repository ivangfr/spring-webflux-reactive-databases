package com.ivanfranchin.productapi.rest.dto;

import com.ivanfranchin.productapi.model.Product;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, BigDecimal price) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }
}
