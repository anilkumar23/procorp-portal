package com.procorp.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.procorp.post.config.PostRequestDeserializer;
import com.procorp.post.dto.PostRequestDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:application.yml")
public class FileStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageServiceApplication.class, args);
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
		module.addDeserializer(PostRequestDto.class, new PostRequestDeserializer());
		return module;
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
