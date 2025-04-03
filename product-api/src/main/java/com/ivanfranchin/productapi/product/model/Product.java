package com.ivanfranchin.productapi.product.model;

import com.ivanfranchin.productapi.product.dto.CreateProductRequest;
import com.ivanfranchin.productapi.product.dto.UpdateProductRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private BigDecimal price;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static Product from(CreateProductRequest createProductRequest) {
        Product product = new Product();
        product.setName(createProductRequest.name());
        product.setPrice(createProductRequest.price());
        return product;
    }

    public static void updateFrom(UpdateProductRequest updateProductRequest, Product product) {
        if (updateProductRequest.name() != null) {
            product.setName(updateProductRequest.name());
        }
        if (updateProductRequest.price() != null) {
            product.setPrice(updateProductRequest.price());
        }
    }
}
