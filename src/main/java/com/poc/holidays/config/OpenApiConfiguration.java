package com.poc.holidays.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("Holiday Management POC")
                .version("1.0")
                .contact(getContact())
                .description("This project is a POC for Holiday Management").termsOfService("Copyright © 2025 HCLtech.com, All Rights Reserved.");

        return new OpenAPI().info(info);
    }

    private Contact getContact(){
        Contact contact = new Contact();
        contact.setEmail("satishkota1729@gmail.com");
        contact.setName("HCLtech");
        return contact;
    }
}
