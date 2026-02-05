package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedUserInfoException extends ApiException {
    public DuplicatedUserInfoException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
