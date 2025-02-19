package com.peakyapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
        info = @Info(title = "Peaky Blinders API", version = "1.0", description = "API for managing Peaky Blinders resources")
)
@SpringBootApplication
public class PeakyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeakyApiApplication.class, args);
    }

}
