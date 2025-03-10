package com.zerry.flix_auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerry.flix_auth.exception.TokenRefreshException;
import com.zerry.flix_auth.model.RefreshToken;
import com.zerry.flix_auth.model.User;
import com.zerry.flix_auth.repository.RefreshTokenRepository;
import com.zerry.flix_auth.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) throws TokenRefreshException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token expired. Please sign in again.");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}