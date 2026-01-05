package com.book_heaven.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ActiveUserNotFoundException extends RuntimeException {
    public ActiveUserNotFoundException(String message) {
        super(message);
    }
}
