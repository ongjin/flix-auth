package com.zerry.auth.global.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final String code;
    private final String message;

    public AuthException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AuthException(String message) {
        this("AUTH_ERROR", message);
    }
}