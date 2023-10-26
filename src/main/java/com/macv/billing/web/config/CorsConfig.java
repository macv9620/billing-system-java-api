package com.macv.billing.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    //En este método se definen todas las reglas que aplican al proyecto
    //en términos de CORS
    CorsConfigurationSource corsConfigurationSource(){
        //Objeto que permite configurar las reglas de CORS
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Métodos permitidos
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));

        //Origenes permitidos
        corsConfiguration.setAllowedOrigins(List.of("*"));

        //Hedears: en este caso se permiten rodos oc
        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //Registrar una configuración de CORS a todos los paths /** y con lo definido en corsConfiguration
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
