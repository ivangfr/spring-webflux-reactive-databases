package com.mycompany.productapi.service;

import com.mycompany.productapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> validateAndGetProduct(String id);

    Flux<Product> getProducts();

    Mono<Product> saveProduct(Product product);

    Mono<Void> deleteProduct(Product product);

}
