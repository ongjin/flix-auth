package com.zerry.auth.domain.auth.dto;

import com.zerry.auth.domain.user.entity.RoleEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private List<RoleEnum> roles;
    private boolean enabled;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}