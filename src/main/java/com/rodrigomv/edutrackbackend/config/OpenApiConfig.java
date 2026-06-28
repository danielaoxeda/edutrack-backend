package com.rodrigomv.edutrackbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduTrack API")
                        .description("Documentación de la API REST de EduTrack")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("EduTrack")
                                .email("soporte@edutrack.com"))
                        .license(new License().name("Uso académico")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://github.com/danielaoxeda/edutrack-backend"));
    }
}
