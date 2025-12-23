package com.example.accelerator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI clinicalAssessmentOpenAPI() {

        return new OpenAPI()

                // 🔐 BASIC AUTH configuration
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "basicAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                )
                )

                // 🔐 Apply Basic Auth globally
                .addSecurityItem(
                        new SecurityRequirement().addList("basicAuth")
                )

                // 📄 API Information
                .info(
                        new Info()
                                .title("Clinical Assessment Builder API")
                                .description("""
                                        Backend APIs for building clinical assessments:
                                        - Assessment CRUD
                                        - Question Builder
                                        - Conditional Logic
                                        - Preview Simulation
                                        - Admin-only access (Basic Auth)
                                        """)
                                .version("1.0.0")
                )

                // 🌍 Server Configurations
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development"),
                        new Server()
                                .url("http://localhost:8081")
                                .description("Production")
                ));
    }
}

