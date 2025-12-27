package com.example.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {

    private final Key key;
    private final long validityInMillis;

    public JwtTokenProvider(String secret, long validityInMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMillis = validityInMillis;
    }

    public String generateToken(Authentication authentication,
                                Long userId,
                                String email,
                                String role) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    public String getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
