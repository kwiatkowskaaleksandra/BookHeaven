package com.book_heaven.backend.exception;


import org.springframework.http.HttpStatus;

public class ActiveUserNotFoundException extends ApiException {
    public ActiveUserNotFoundException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
