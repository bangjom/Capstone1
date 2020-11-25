package com.capstone.studywithme.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    private Key key;

    public JwtUtil(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long id, String email){

        String token = Jwts.builder()
                .claim("userId",id)
                .claim("email",email)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }
}
