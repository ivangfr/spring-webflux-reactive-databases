package com.mycompany.productapi.rest;

import com.mycompany.productapi.mapper.ProductMapper;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.rest.dto.CreateProductDto;
import com.mycompany.productapi.rest.dto.ProductDto;
import com.mycompany.productapi.rest.dto.UpdateProductDto;
import com.mycompany.productapi.service.ProductService;
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

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDto> getProducts() {
        return productService.getProducts().map(productMapper::toProductDto);
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id).map(productMapper::toProductDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<ProductDto> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        Product product = productMapper.toProduct(createProductDto);
        return productService.saveProduct(product).map(productMapper::toProductDto);
    }

    @PatchMapping("/{id}")
    public Mono<ProductDto> updateProduct(@PathVariable String id, @RequestBody UpdateProductDto updateProductDto) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> {
                    productMapper.updateProductFromDto(updateProductDto, product);
                    productService.saveProduct(product).subscribe();
                })
                .map(productMapper::toProductDto);
    }

    @DeleteMapping("/{id}")
    public Mono<ProductDto> deleteProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> productService.deleteProduct(product).subscribe())
                .map(productMapper::toProductDto);
    }
}
