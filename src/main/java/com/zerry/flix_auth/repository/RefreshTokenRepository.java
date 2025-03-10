package com.zerry.flix_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerry.flix_auth.model.RefreshToken;
import com.zerry.flix_auth.model.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
