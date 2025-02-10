package com.glassvisionai.glassvisionai.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                antMatcher("/v3/api-docs/**"),  // OpenAPI JSON
                                antMatcher("/swagger-ui/**"),   // Swagger UI
                                antMatcher("/swagger-ui.html")  // Swagger UI Entry
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()) // Use `withDefaults()` instead of `httpBasic()`
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        return http.build();
    }
}
