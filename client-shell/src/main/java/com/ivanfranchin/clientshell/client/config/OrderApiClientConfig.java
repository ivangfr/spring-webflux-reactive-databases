package com.ivanfranchin.clientshell.client.config;

import com.ivanfranchin.clientshell.client.OrderApiClient;
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
    public OrderApiClient orderApiClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(orderApiUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return factory.createClient(OrderApiClient.class);
    }
}
