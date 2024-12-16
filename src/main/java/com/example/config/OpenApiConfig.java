package com.example.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        //http://localhost:8080/swagger-ui/index.html
        return new OpenAPI().info(new Info().title("Currency Exchange API").version("1.0"));
    }
}
