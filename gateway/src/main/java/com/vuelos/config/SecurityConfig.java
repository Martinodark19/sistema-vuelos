package com.vuelos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Usamos la API actualizada para deshabilitar CSRF
                .authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/user/login/**").permitAll()  // Permitir acceso sin token a rutas públicas
                    .pathMatchers("/user/signup/**").permitAll()  // Permitir acceso sin token a rutas públicas
                    .pathMatchers("/user/prueba/**").permitAll()  // Permitir acceso sin token a rutas públicas

                    .anyExchange().authenticated()          // Requerir autenticación en todas las demás rutas
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)  // Añadimos el filtro JWT con su respectivo orden 
                .build();
    }
}
