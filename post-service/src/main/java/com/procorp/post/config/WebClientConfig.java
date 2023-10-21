package com.procorp.post.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${friendsService.base.url}")
    private String friendsServiceBaseUrl;

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(friendsServiceBaseUrl).build();
    }

    @Bean
    public WebClient webClient1() {
        return WebClient.builder().baseUrl(friendsServiceBaseUrl).build();
    }
}
