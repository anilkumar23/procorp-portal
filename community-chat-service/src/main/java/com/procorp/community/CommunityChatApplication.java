package com.procorp.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.procorp.community.dtos.CommunityChatHistoryDeserializer;
import com.procorp.community.dtos.CommunityChatHistoryDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
public class CommunityChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunityChatApplication.class, args);
    }
    @Bean
    ObjectMapper customizejacksonconfiguration() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new GuavaModule());
        return om;
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @Bean
    public SimpleModule communityChatHistoryDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CommunityChatHistoryDto.class, new CommunityChatHistoryDeserializer());
        return module;
    }
}