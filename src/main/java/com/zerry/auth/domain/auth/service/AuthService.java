package com.zerry.auth.domain.auth.service;

import com.zerry.auth.domain.auth.dto.LoginRequest;
import com.zerry.auth.domain.auth.dto.SignupRequest;
import com.zerry.auth.domain.auth.dto.TokenResponse;
import com.zerry.auth.domain.user.entity.Role;
import com.zerry.auth.domain.user.entity.User;
import com.zerry.auth.domain.user.repository.RoleRepository;
import com.zerry.auth.domain.user.repository.UserRepository;
import com.zerry.auth.domain.user.service.CustomUserDetailsService;
import com.zerry.auth.global.config.JwtConfig;
import com.zerry.auth.global.exception.AuthException;
import com.zerry.auth.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public TokenResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("Username already exists");
        }

        // Get or create ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        // Create User
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        String refreshToken = generateRefreshToken();

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(roles)
                .refreshToken(refreshToken)
                .build();

        userRepository.save(user);

        // Create authentication token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                request.getPassword(),
                userDetails.getAuthorities());
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        return TokenResponse.of(accessToken, refreshToken, jwtTokenProvider.getAccessTokenExpirationTime());
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        // 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 인증된 사용자 정보 가져오기
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));

        // 새로운 리프레시 토큰 생성 및 저장
        String refreshToken = generateRefreshToken();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        return TokenResponse.of(accessToken, refreshToken, jwtTokenProvider.getAccessTokenExpirationTime());
    }

    @Transactional
    public TokenResponse refreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthException("Invalid refresh token"));

        // 새로운 리프레시 토큰 생성 및 저장
        String newRefreshToken = generateRefreshToken();
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        // Create authentication token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        return TokenResponse.of(accessToken, newRefreshToken, jwtTokenProvider.getAccessTokenExpirationTime());
    }

    @Transactional
    public void logout(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthException("Invalid refresh token"));

        user.removeRefreshToken();
        userRepository.save(user);
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}