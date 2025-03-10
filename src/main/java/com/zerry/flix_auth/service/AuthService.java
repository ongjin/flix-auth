package com.zerry.flix_auth.service;

import com.zerry.flix_auth.model.AuthResponse;

/**
 * AuthService 인터페이스
 * 사용자 인증을 위한 메서드 시그니처를 정의합니다.
 */
public interface AuthService {
    /**
     * 사용자 인증을 수행하고 JWT 토큰을 반환합니다.
     * 
     * @param username 사용자명
     * @param password 비밀번호
     * @return JWT 토큰
     */
    AuthResponse authenticate(String username, String password);
}
