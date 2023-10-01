package com.procorp.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.procorp.chat.dtos.ChatHistoryDTO;
import com.procorp.chat.dtos.ChatHistoryDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    ObjectMapper customizejacksonconfiguration() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new GuavaModule());
        return om;
    }

    @Bean
    public SimpleModule chatHistoryDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ChatHistoryDTO.class, new ChatHistoryDeserializer());
        return module;
    }
}
