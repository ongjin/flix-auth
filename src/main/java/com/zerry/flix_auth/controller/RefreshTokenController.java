package com.zerry.flix_auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zerry.flix_auth.exception.TokenRefreshException;
import com.zerry.flix_auth.model.AuthResponse;
import com.zerry.flix_auth.model.TokenRefreshRequest;
import com.zerry.flix_auth.response.ApiResponse;
import com.zerry.flix_auth.service.RefreshTokenService;
import com.zerry.flix_auth.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody TokenRefreshRequest request) {
        var refreshTokenOpt = refreshTokenService.findByToken(request.getRefreshToken());
        var refreshToken = refreshTokenOpt
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Refresh token not found."));
        refreshTokenService.verifyExpiration(refreshToken);
        String newAccessToken = JwtUtil.generateToken(refreshToken.getUser().getUsername());
        return ResponseEntity.ok(ApiResponse.success(new AuthResponse(newAccessToken, refreshToken.getToken())));
    }
}
