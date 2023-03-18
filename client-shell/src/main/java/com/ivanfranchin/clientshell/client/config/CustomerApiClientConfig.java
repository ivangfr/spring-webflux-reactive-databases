package com.ivanfranchin.clientshell.client.config;

import com.ivanfranchin.clientshell.client.CustomerApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CustomerApiClientConfig {

    @Value("${customer-api.url}")
    private String customerApiUrl;

    @Bean
    public CustomerApiClient customerApiClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(customerApiUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return factory.createClient(CustomerApiClient.class);
    }
}
