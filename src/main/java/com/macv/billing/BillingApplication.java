package com.macv.billing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(
		info = @Info(
				title = "Java Spring Boot - Billing System API",
				description = "API Rest desplegada en AWS que gestiona el sistema de inventario y facturación de un" +
						" comercio electrónico, implementa una base de datos creada en PostgreSQL",
				version = "1.3"
		)
)
public class BillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);
	}

}
