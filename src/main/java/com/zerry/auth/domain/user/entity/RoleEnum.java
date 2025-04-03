package com.zerry.auth.domain.user.entity;

public enum RoleEnum {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자"),
    ROLE_BUYER("구매자");

    private final String description;

    RoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name();
    }
}