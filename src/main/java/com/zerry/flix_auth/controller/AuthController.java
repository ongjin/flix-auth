package com.zerry.flix_auth.controller;

import com.zerry.flix_auth.model.AuthRequest;
import com.zerry.flix_auth.model.AuthResponse;
import com.zerry.flix_auth.response.ApiResponse;
import com.zerry.flix_auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
 * /auth/login 엔드포인트를 통해 사용자 인증 및 JWT 토큰 발급을 담당합니다.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /auth/login
     * 사용자 인증을 수행하고, JWT 토큰을 반환합니다.
     *
     * @param authRequest 사용자명과 비밀번호를 포함한 요청
     * @return JWT 토큰을 포함한 응답
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(ApiResponse.success(authResponse));
    }

}