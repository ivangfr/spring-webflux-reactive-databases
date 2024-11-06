package com.ivanfranchin.notificationapi.client.config;

import com.ivanfranchin.notificationapi.client.OrderApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class OrderApiClientConfig {

    @Value("${order-api.url}")
    private String orderApiUrl;

    @Bean
    public OrderApiClient orderApiClient(WebClient.Builder builder) {
        WebClient webClient = builder.baseUrl(orderApiUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(OrderApiClient.class);
    }
}
