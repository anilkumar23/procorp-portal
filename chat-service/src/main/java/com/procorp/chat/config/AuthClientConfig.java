package com.procorp.chat.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class AuthClientConfig {

    @Value("${authservice.base.url}")
    private String authServiceBaseUrl;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebClient authClient() {
        return WebClient.builder().baseUrl(authServiceBaseUrl).build();
    }
}
