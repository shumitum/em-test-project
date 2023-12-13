package com.effectivemobile.testproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System API",
                version = "1.0",
                description = "Documentation \"Task Management System API\" v1.0"),

        security = @SecurityRequirement(
                name = "Bearer Authentication"),

        servers = @Server(
                description = "Test Server",
                url = "http://localhost:8080"
        )
)

@SecurityScheme(
        name = "Bearer Authentication",
        description = "A JWT token is required to access this API",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}
