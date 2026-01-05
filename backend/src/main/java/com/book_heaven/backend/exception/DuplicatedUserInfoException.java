package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedUserInfoException extends RuntimeException {
    public DuplicatedUserInfoException(final String message) {
        super(message);
    }
}
