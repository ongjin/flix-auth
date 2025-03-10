package com.zerry.flix_auth.exception;

/**
 * InvalidCredentialsException
 * 사용자 인증 실패 시 발생하는 예외입니다.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}