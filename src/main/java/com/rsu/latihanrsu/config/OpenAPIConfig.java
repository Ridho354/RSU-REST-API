package com.rsu.latihanrsu.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "Latihan RSU",
    description = "API for Rumah Sakit",
    version = "1.0",
    contact=@Contact(
        name = "Latihan RSU",
        url = "https://latihan-rsu.com",
        email = "ridho@gmail.com"
    )
    ), security = @SecurityRequirement(name = "bearer Authentication")
)
@SecurityScheme(name = "bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenAPIConfig {

}
