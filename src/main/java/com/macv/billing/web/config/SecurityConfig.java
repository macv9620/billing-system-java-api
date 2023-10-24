package com.macv.billing.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> {
                    csrfConfig.disable();
                })
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(customizeRequests -> {
                    customizeRequests
                            .requestMatchers("/v2/api-docs/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html/**", "/webjars/**", "/swagger-ui/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/auth/login/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/brand/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                            //Ojo con el orden de la cadena analizar si hay un permiso o configuración "superior"
                            //que se ejecute antes y anule el permiso que se desea
                            .requestMatchers(HttpMethod.POST, "/api/invoice/newBuy").hasAuthority("create_invoice")
                            .requestMatchers(HttpMethod.POST,"/api/invoice/**").hasAnyRole( "ADMIN")
                            .requestMatchers(HttpMethod.DELETE).denyAll()
                            .requestMatchers("/**").hasRole("ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //Crear el authenticationManager para poderlo inyectar en el AuthController
    //Desde el controller se le enviará un AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        //Con base en ese AutenticationConfiguration configuration se retorna
        //el AuthenticationManager que se tiene Spring por defecto
        return configuration.getAuthenticationManager();
    }

    //Encoder que provee Spring Security
    @Bean
    public PasswordEncoder passwordEncoder(){
        //Tipo de codificado comunmente empleado
        return new BCryptPasswordEncoder();
    }
}
