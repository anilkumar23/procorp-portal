package com.procorp.chat.config;

import com.procorp.chat.dtos.FriendRequestDTO;
import com.procorp.chat.exception.ChatIllegalStateException;
import com.procorp.chat.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
@Configuration
public class ChatServiceAspect {
    //TODO Need to handle case for Connection Refused
    @Autowired
    private WebClient authClient;

    @Before("execution(* com.procorp.chat.service..*(..))")
    private boolean anyStudentService() {
        String s = FeignClientInterceptor.getBearerTokenHeader();
        Mono<Object> flag = authClient.get().uri("/isAuthenticated")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",s)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        try {
                return Objects.requireNonNull(flag.block()).toString().equalsIgnoreCase("true");
        } catch (Exception authenticationException) {
            if(authenticationException.toString().contains("401")){
                throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
            }
        }
        return false;
    }
}
