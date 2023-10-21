package com.macv.billing.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                            .requestMatchers(HttpMethod.GET, "/api/product/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/brand/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/category/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/invoice/newBuy/**").hasAnyRole("CUSTOMER", "ADMIN")
                            .requestMatchers(HttpMethod.DELETE).denyAll()
                            .requestMatchers("/**").hasRole("ADMIN")
                            .anyRequest()
                            .authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //Encoder que provee Spring Security
    @Bean
    public PasswordEncoder passwordEncoder(){
        //Tipo de codificado comunmente empleado
        return new BCryptPasswordEncoder();
    }
}
