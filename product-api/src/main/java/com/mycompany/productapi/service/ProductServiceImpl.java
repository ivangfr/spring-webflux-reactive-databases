package com.mycompany.productapi.service;

import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> validateAndGetProduct(String id) {
        return productRepository.findById(id).switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    @Override
    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> deleteProduct(Product product) {
        return productRepository.delete(product);
    }

}
