package com.ivanfranchin.clientshell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class RandomConfig {

    @Bean
    Random random() {
        return new SecureRandom();
    }
}
