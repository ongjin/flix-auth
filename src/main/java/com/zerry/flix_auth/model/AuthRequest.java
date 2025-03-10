package com.zerry.flix_auth.model;

import lombok.Data;

/**
 * AuthRequest
 * 로그인 요청 데이터 (사용자명, 비밀번호)
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}