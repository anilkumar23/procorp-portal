package com.procorp.community.config;

import com.procorp.community.exception.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Aspect
@Component
@Configuration
public class ChatServiceAspect {
    //TODO Need to handle case for Connection Refused
    @Autowired
    private WebClient authClient;
    @Value("${authservice.base.url}")
    private String authServiceBaseUrl;
    @Before("execution(* com.procorp.community.service..*(..))")
    private boolean anyStudentService() {
        String token = FeignClientInterceptor.getBearerTokenHeader();
        if (!StringUtils.hasText(token)) throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
        String[] tokenFields = token.split(" ");
        Mono<Object> flag = authClient.get().uri(authServiceBaseUrl+"?token=" + tokenFields[1])
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        try {
            if (!Objects.requireNonNull(flag.block()).toString().equalsIgnoreCase("true")) throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
        } catch (Exception authenticationException) {
                throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
        }
        return true;
    }
}
