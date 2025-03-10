package com.zerry.flix_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerry.flix_auth.model.User;

import java.util.Optional;

/**
 * UserRepository
 * 사용자 조회를 위한 인터페이스입니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}