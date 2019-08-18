package com.mycompany.productapi.rest;

import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.rest.dto.CreateProductDto;
import com.mycompany.productapi.rest.dto.ProductDto;
import com.mycompany.productapi.rest.dto.UpdateProductDto;
import com.mycompany.productapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final MapperFacade mapperFacade;

    @GetMapping
    public Flux<ProductDto> getProducts() {
        return productService.getProducts()
                .map(product -> mapperFacade.map(product, ProductDto.class));
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id)
                .map(p -> mapperFacade.map(p, ProductDto.class));
    }

    @PostMapping
    public Mono<ProductDto> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        Product product = mapperFacade.map(createProductDto, Product.class);
        return productService.saveProduct(product)
                .map(p -> mapperFacade.map(p, ProductDto.class));
    }

    @PutMapping("/{id}")
    public Mono<ProductDto> updateProduct(@PathVariable String id, @RequestBody UpdateProductDto updateProductDto) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> {
                    mapperFacade.map(updateProductDto, product);
                    productService.saveProduct(product).subscribe();
                })
                .map(product -> mapperFacade.map(product, ProductDto.class));
    }

    @DeleteMapping("/{id}")
    public Mono<ProductDto> deleteProduct(@PathVariable String id) {
        return productService.validateAndGetProduct(id)
                .doOnSuccess(product -> productService.deleteProduct(product).subscribe())
                .map(product -> mapperFacade.map(product, ProductDto.class));
    }

}
