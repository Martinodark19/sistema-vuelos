package com.vuelos.config.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;


@Component
public class JwtUtils 
{

    @Value("${security.jwt.user.generator}")
    private String emisorToken; 

    @Value("${security.jwt.key.private}")
    private String secretKey ; 

    // este metodo sera el encargado de decodificar el token recibido
    public Mono<DecodedJWT> decodedJWT(String token) 
    {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
                JWTVerifier jwtVerifier = JWT.require(algorithm)
                        .withIssuer(this.emisorToken)
                        .build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                System.out.println("Se decodificó correctamente en el gateway: " + decodedJWT);
                return Mono.just(decodedJWT);
            } 
            catch (JWTDecodeException e) 
            {
                System.out.println("No se decodificó");
                return Mono.error(new JWTDecodeException("Ha ocurrido un error, no tienes autorización para acceder a este recurso", e));
            }
        });
    }
}
