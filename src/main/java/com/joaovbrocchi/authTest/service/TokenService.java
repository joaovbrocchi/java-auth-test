package com.joaovbrocchi.authTest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.joaovbrocchi.authTest.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private Algorithm  algorithm = Algorithm.HMAC256(secret);
    public String generateToken(User user){
        try {

            String token = JWT
                    .create()
                    .withIssuer("auth-test")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExperirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException e){
            throw new RuntimeException("error while trying to genrate token", e);

        }
    }

    public String validateToken(String token){
        try {
            return JWT.require(algorithm)
                    .withIssuer("auth-test")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e){
            return "";
        }
    }
    private Instant generateExperirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
