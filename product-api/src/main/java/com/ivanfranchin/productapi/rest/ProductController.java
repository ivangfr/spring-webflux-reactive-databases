package com.ivanfranchin.productapi.rest;

import com.ivanfranchin.productapi.model.Product;
import com.ivanfranchin.productapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.productapi.rest.dto.ProductResponse;
import com.ivanfranchin.productapi.rest.dto.UpdateProductRequest;
import com.ivanfranchin.productapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductResponse> getProducts() {
        return productService.getProducts().map(ProductResponse::from);
    }

    @GetMapping("/{id}")
    public Mono<ProductResponse> getProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id).map(ProductResponse::from);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = Product.from(createProductRequest);
        return productService.saveProduct(product).map(ProductResponse::from);
    }

    @PatchMapping("/{id}")
    public Mono<ProductResponse> updateProduct(@PathVariable String id,
                                               @RequestBody UpdateProductRequest updateProductRequest) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> {
                    Product.updateFrom(updateProductRequest, product);
                    productService.saveProduct(product).subscribe();
                })
                .map(ProductResponse::from);
    }

    @DeleteMapping("/{id}")
    public Mono<ProductResponse> deleteProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> productService.deleteProduct(product).subscribe())
                .map(ProductResponse::from);
    }
}
