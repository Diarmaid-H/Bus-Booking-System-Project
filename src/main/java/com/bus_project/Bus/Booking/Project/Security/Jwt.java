package com.bus_project.Bus.Booking.Project.Security;

import java.security.Key;
import java.util.Date;
import java.util.Base64;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Jwt {

    private final Key key;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    //Converts SECRET_KEY into a secure key for signing JWTs
    public Jwt(@Value("${jwt.secret}") String secretKey) {
        byte[] decodedKey = Base64.getDecoder().decode(Base64.getEncoder().encodeToString(secretKey.getBytes()));
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateTokenAndGetEmail(String token) {
        try {
            System.out.println("Validating token: " + token);

            Claims claims = Jwts.parserBuilder() 
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) 
                    .getBody();

            System.out.println("Token is valid. Extracted Email: " + claims.getSubject()); 
            return claims.getSubject();
        } catch (JwtException e) {
            return null; // Invalid token
        }
    }
}

