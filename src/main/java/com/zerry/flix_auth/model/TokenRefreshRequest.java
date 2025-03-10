package com.zerry.flix_auth.model;

import lombok.Data;

/**
 * TokenRefreshRequest
 * 리프레시 토큰 재발급 요청 데이터를 담습니다.
 */
@Data
public class TokenRefreshRequest {
    private String refreshToken;
}