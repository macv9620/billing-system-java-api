package com.macv.billing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@OpenAPIDefinition(
		info = @Info(
				title = "Java Spring Boot - Billing System API",
				description = "API Rest desplegada en AWS que gestiona el sistema de inventario y facturación de un" +
						" comercio electrónico, implementa una base de datos PostgreSQL, " +
						"Spring Security para la gestión de autenticación (basada en JWT) y autorización bajo los roles 'ADMIN' y 'CUSTOMER', " +
						"contiene algunos endpoints de acceso público y algunos de acceso restringido, se expone un endpoint " +
						"abierto para el inicio de sesión y la obtención del Token necesario para las operaciones.",
				version = "1.3"
		)
)
@Configuration
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer",
		description = "Algunos endpoints se encuentran protegidos y requieren JWT para ser consumidos, inicia sesión usando el método login del Auth-Controller con el body de ejemplo, agrega el JWT retornado en la siguiente casilla para que el token sea incorporado en las peticiones protegidas:"
)
public class BillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);
	}

}
