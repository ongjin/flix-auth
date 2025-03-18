package com.zerry.flix_auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerry.flix_auth.exception.InvalidCredentialsException;
import com.zerry.flix_auth.model.AuthResponse;
import com.zerry.flix_auth.model.User;
import com.zerry.flix_auth.repository.UserRepository;
import com.zerry.flix_auth.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthServiceImpl
 * AuthService의 기본 구현체로, 사용자명과 비밀번호가 "user"/"password"일 경우 JWT 토큰을 생성합니다.
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password credentials");
        }
        String accessToken = jwtUtil.generateToken(username, user.getId());
        var refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken.getToken());
    }
}
