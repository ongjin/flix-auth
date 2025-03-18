package com.zerry.flix_auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * JwtUtil
 * JWT 토큰 생성 및 검증에 관련된 유틸리티 클래스입니다.
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "abc123qweDG";
    private static final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1시간

    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("userId", userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("userId", Long.class); // userId 추출
    }
}
