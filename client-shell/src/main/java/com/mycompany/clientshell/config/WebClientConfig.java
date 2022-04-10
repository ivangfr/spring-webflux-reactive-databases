package com.mycompany.clientshell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${product-api.url}")
    private String productApiUrl;

    @Value("${customer-api.url}")
    private String customerApiUrl;

    @Value("${order-api.url}")
    private String orderApiUrl;

    @Value("${notification-api.url}")
    private String notificationApiUrl;

    @Bean(name = "productApiWebClient")
    WebClient productApiWebClient() {
        return WebClient.create(productApiUrl);
    }

    @Bean(name = "customerApiWebClient")
    WebClient customerApiWebClient() {
        return WebClient.create(customerApiUrl);
    }

    @Bean(name = "orderApiWebClient")
    WebClient orderApiWebClient() {
        return WebClient.create(orderApiUrl);
    }

    @Bean(name = "notificationApiWebClient")
    WebClient notificationApiWebClient() {
        return WebClient.create(notificationApiUrl);
    }
}
