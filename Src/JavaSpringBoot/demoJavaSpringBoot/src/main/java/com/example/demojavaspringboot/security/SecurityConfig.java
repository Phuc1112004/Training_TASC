package com.example.demojavaspringboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())   // tắt CSRF để Postman POST được
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // mở toàn bộ API
                );
        return http.build();
    }
}

