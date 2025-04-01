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
    ProductApiClient productApiClient(WebClient.Builder builder) {
        WebClient webClient = builder.baseUrl(productApiUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(ProductApiClient.class);
    }
}
