package com.mycompany.clientshell.client;

import com.mycompany.clientshell.dto.CreateProductDto;
import com.mycompany.clientshell.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class ProductApiClient {

    @Autowired
    @Qualifier("productApiWebClient")
    private WebClient webClient;

    public Mono<ProductDto> getProduct(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getProducts() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(ProductDto.class);

    }

    public Mono<ProductDto> createProduct(String name, BigDecimal price) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(new CreateProductDto(name, price))
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> deleteProduct(String id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/{id}").build(id))
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

}
