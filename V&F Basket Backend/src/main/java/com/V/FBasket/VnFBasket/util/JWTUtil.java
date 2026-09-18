package com.V.FBasket.VnFBasket.util;

import com.V.FBasket.VnFBasket.dao.UserRepository;
import com.V.FBasket.VnFBasket.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private UserRepository userRepository;

    @Autowired
    public JWTUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final String SECRET = "my-super-secret-key-that-should-be-long-enough";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = 1000*60*60; // 1 hr

    public String generateToken(String username) {
        User user = userRepository.findByEmail(username).get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", username);
        claims.put("role", user.getRole());
        claims.put("userId", user.getUserId());

        return Jwts.builder()
                .setClaims(claims)                    // Set claims FIRST
                .setSubject(username)                  // Then set subject AFTER claims
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        Claims claims;
        try{
            claims = Jwts
                    .parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(Exception e){
            claims = null;
        }
        return claims;
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}