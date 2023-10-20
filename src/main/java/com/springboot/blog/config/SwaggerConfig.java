package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final  String AUTHORIZATION_HEADER = "Authorization";

    public ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Spring Boot Blog App Rest Api",
                "Spring boot Blog App Rest Api`s Documentation",
                "1",
                "Terms of Service",
                new Contact("Musa Usman", "https:/www.linkedin.com/in/usman-musa-profile0003/", "musausmanjb@gmail.com"),
                "licence of API ...",
                "Api licence Url",
                Collections.emptyList()
        );

    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext())) //Collections.singletonList(securityContext())
                .securitySchemes(Arrays.asList(apiKey())) //Collections.singletonList(apiKey())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("JWT", "accessToEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
