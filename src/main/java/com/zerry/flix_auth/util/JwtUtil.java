package com.zerry.flix_auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JwtUtil
 * JWT 토큰 생성 및 검증에 관련된 유틸리티 클래스입니다.
 */
public class JwtUtil {
    private static final String SECRET_KEY = "abc123qweDG";
    private static final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1시간

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }
}
