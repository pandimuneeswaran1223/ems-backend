package com.project.ems_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String genereteToken(String userName,String role)
    {
       return Jwts.builder()
                .setSubject(userName)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSignKey())
                .compact();
    }

    public String getUsername(String token)
    {
        return getClaims(token).getSubject();
    }

    public String getRole(String token)
    {
        return getClaims(token).get("role",String.class);
    }

    public boolean isTokenValid(String token,String userName)
    {
        return getUsername(token).equals(userName) && !getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token)
    {
       return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignKey()
    {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
