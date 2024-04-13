package com.ivanfranchin.clientshell.client.config;

import com.ivanfranchin.clientshell.client.NotificationApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class NotificationApiClientConfig {

    @Value("${notification-api.url}")
    private String notificationApiUrl;

    @Bean
    public NotificationApiClient notificationApiClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(notificationApiUrl)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(NotificationApiClient.class);
    }
}
