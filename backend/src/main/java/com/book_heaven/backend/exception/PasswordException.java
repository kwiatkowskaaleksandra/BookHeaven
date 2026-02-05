package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;

public class PasswordException extends ApiException {
    public PasswordException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
