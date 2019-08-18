package com.mycompany.productapi.model;

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

}
