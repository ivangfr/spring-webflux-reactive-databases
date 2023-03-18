package com.ivanfranchin.orderapi.client.config;

import com.ivanfranchin.orderapi.client.ProductApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductApiClientConfig {

    @Value("${product-api.url}")
    private String productApiUrl;

    @Bean
    public ProductApiClient productApiClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(productApiUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return factory.createClient(ProductApiClient.class);
    }
}
