package com.Internova.internova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for now to allow POST from Postman
                .authorizeHttpRequests(auth -> auth
                        // ✅ allow access to H2 console
                        .requestMatchers(toH2Console()).permitAll()

                        // ✅ allow public access to admin endpoints for testing
                        .requestMatchers("/admin/**").permitAll()

                        // everything else needs authentication (for later)
                        .anyRequest().authenticated()
                )
                // allow H2 console frames
                .headers(headers -> headers.frameOptions().sameOrigin())

                // enable basic authentication for secure endpoints
                .httpBasic();

        return http.build();
    }
}