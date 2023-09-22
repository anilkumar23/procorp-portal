//package com.procorp.chat.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import javax.management.QueryExp;
//
//import java.util.function.Predicate;
//
//import static javax.management.Query.or;
//import static springfox.documentation.builders.PathSelectors.regex;
//
//@Configuration
//public class SpringFoxConfig {
//    @Bean
//    public Docket postsApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
//                .apiInfo(apiInfo()).select().paths(postPaths()).build();
//    }
//
//    private Predicate<String> postPaths() {
//        return  or((QueryExp) regex("/api/posts.*"), (QueryExp) regex("/api/javainuse.*"));
//    }
//
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("JavaInUse API")
//                .description("JavaInUse API reference for developers")
//                .termsOfServiceUrl("http://javainuse.com").license("JavaInUse License")
//                .licenseUrl("javainuse@gmail.com").version("1.0").build();
//    }
//
//}
//
