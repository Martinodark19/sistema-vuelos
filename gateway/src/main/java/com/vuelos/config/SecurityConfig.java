package com.vuelos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.vuelos.config.utils.JwtFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig 
{

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) 
    {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) 
    {
        return http   
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // Desactiva autenticación HTTP Basic
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // Desactiva autenticación por login form
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET,"/flight/info").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
