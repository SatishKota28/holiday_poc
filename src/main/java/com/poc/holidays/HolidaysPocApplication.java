package com.poc.holidays;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class HolidaysPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolidaysPocApplication.class, args);
	}

}
