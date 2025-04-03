package com.ivanfranchin.productapi.product;

import com.ivanfranchin.productapi.product.exception.ProductNotFoundException;
import com.ivanfranchin.productapi.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> validateAndGetProduct(String id) {
        return productRepository.findById(id).switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Void> deleteProduct(Product product) {
        return productRepository.delete(product);
    }
}
