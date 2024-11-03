package com.belajar.jwt.belajar.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Menonaktifkan CSRF (Cross-Site Request Forgery) protection
            .authorizeRequests()
            .anyRequest().permitAll() // Mengizinkan semua request tanpa autentikasi
            .and()
            .headers().frameOptions().disable(); // Mengizinkan akses ke H2 Console jika digunakan (opsional)

        return http.build();
    }
}


