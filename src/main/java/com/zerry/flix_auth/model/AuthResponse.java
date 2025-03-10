package com.zerry.flix_auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthResponse
 * 로그인 성공 시 액세스 토큰과 리프레시 토큰을 반환합니다.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}