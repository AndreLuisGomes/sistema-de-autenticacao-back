package com.casafacil.casa_facil_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.casafacil.casa_facil_api.domain.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Data
public class TokenService {

    @Value("${my.secret.api.key}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("casa-facil-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating!");
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3));
    }

}
