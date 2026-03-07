package com.app.todoapp.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


// 负责生成和解析tokens
@Component
public class JwtUtil {

    public static final String SECRET = "tenny-is-a-nice-person-ma-chen-chen-520";    // signature
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    public static final Duration EXPIRATION_TIME = Duration.ofHours(24);         // expiration: 24 hours

    // Generate token with userId
    public String generateToken(Long userId) {
        return Jwts.builder().setSubject(userId.toString())
                .setIssuedAt(Date.from(Instant.now())).setExpiration(Date.from(Instant.now().plus(EXPIRATION_TIME)))
                        .signWith(SECRET_KEY).compact();
    }

    // Decrypt token to get userId
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
