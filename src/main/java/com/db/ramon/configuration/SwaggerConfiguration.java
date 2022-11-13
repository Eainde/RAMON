package com.db.ramon.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RAMON")
                        .version("1.0.0")
                        .description("RAMON")
                        .termsOfService("terms of service")
                        .license(new License().name("1.0").url("http://springdoc.org")));
    }
}
