package com.zerry.flix_auth.service;

import java.util.Optional;

import com.zerry.flix_auth.exception.TokenRefreshException;
import com.zerry.flix_auth.model.RefreshToken;
import com.zerry.flix_auth.model.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(RefreshToken token) throws TokenRefreshException;

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);
}