package com.procorp.post.config;

import com.procorp.post.exception.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Aspect
@Component
@Configuration
public class PostServiceAspect {
    //TODO Need to handle case for Connection Refused
    @Autowired
    private RestTemplate restTemplate;
    @Value("${authService.base.url}")
    private String authServiceBaseUrl;
    @Before("execution(* com.procorp.post.service..*(..))")
    private boolean anyStudentService() {
        String accessToken = FeignClientInterceptor.getBearerTokenHeader();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", accessToken);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            ResponseEntity<Boolean> response = null;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authServiceBaseUrl+"?token=" + accessToken);

            response = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET,
                    entity, Boolean.class);
            if (response.getBody() != null)
                return response.getBody();
        }catch (Exception authenticationException) {
            if(authenticationException.toString().contains("401")){
                throw new UnauthorizedException("Invalid Bearer token or token has expired, please refresh token and give a try!!");
            }
        }
        return false;
    }
}
