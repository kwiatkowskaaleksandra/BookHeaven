package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;

public class UserException extends ApiException {
    public UserException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
