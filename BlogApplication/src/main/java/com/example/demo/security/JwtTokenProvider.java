package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET =
            "mysecretkeymysecretkeymysecretkey";

    private final long JWT_EXPIRATION = 604800000;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate =
                new Date(currentDate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(
                        Keys.hmacShaKeyFor(
                                JWT_SECRET.getBytes()
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String getUsername(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                JWT_SECRET.getBytes()
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    JWT_SECRET.getBytes()
                            )
                    )
                    .build()
                    .parse(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
