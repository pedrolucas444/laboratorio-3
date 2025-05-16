package com.example.moeda.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Mérito Acadêmico API")
                        .version("1.0.0")
                        .description("API para gerenciamento do sistema de mérito com moedas virtuais")
                        .contact(new Contact()
                                .name("Suporte")
                                .email("suporte@example.com")));
    }
}