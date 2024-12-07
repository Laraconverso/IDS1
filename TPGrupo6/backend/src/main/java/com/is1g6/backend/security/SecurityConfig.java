package com.is1g6.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter authFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/send-email").authenticated()

                        .requestMatchers(GET, "/products").permitAll()
                        .requestMatchers(GET, "/products/**").permitAll()
                        .requestMatchers(POST, "/products").hasAuthority("ADMIN")
                        .requestMatchers(PATCH, "/products/**").hasAuthority("ADMIN")
                        .requestMatchers(DELETE, "/products/**").hasAuthority("ADMIN")

                        .requestMatchers(GET, "/pedidos/**").authenticated()
                        .requestMatchers(POST, "/pedidos").authenticated()
                        .requestMatchers(GET, "/pedidos/user/**").authenticated()
                        .requestMatchers(GET, "/pedidos/cancel/**").authenticated()
                        .requestMatchers(GET, "/pedidos").hasAuthority("ADMIN")
                        .requestMatchers(PATCH, "/pedidos").hasAuthority("ADMIN")
                        .requestMatchers(DELETE, "/pedidos").hasAuthority("ADMIN")

                        .requestMatchers(GET, "/users").hasAuthority("ADMIN")
                        .requestMatchers(GET, "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(PUT, "/users/**").authenticated()
                        .requestMatchers(DELETE, "/users/**").authenticated()

                        .requestMatchers("/validators").hasAuthority("ADMIN")

                        .anyRequest().permitAll())
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
