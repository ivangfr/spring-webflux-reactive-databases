package com.mycompany.notificationapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${order-api.url}")
    private String orderApiUrl;

    @Value("${customer-api.url}")
    private String customerApiUrl;

    @Bean(name = "orderApiWebClient")
    WebClient orderApiWebClient() {
        return WebClient.create(orderApiUrl);
    }

    @Bean(name = "customerApiWebClient")
    WebClient customerApiWebClient() {
        return WebClient.create(customerApiUrl);
    }
}
