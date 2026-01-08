package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookException extends RuntimeException {
    public BookException(String message){super(message);}
}
