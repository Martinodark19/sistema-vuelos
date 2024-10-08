package com.vuelos.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig 
{
     @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("microservicio-usuario", r -> r.path("/user/**")
                        .uri("http://user-service:8084"))
                .route("microservicio-vuelos", r -> r.path("/flight/**")
                        .uri("http://user-service:8085"))
                .build();
    }
}
