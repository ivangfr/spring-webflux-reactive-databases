package com.mycompany.orderapi.client;

import com.mycompany.orderapi.client.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductApiClient {

    @Autowired
    @Qualifier("productApiWebClient")
    private WebClient webClient;

    public Mono<ProductResponse> getProduct(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{productId}").build(id))
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }
}
