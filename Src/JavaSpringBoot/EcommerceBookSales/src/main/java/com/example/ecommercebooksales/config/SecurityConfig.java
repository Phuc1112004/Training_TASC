package com.example.ecommercebooksales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép tất cả request
                )
                .httpBasic(Customizer.withDefaults())  // Optional: nếu muốn Basic Auth
                .formLogin(Customizer.withDefaults())  // Optional: nếu muốn form login
                .csrf(csrf -> csrf.disable());         // Tắt CSRF
        return http.build();
    }
}

