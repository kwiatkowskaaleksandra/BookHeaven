package com.book_heaven.backend.exception;

import org.springframework.http.HttpStatus;

public class BookException extends ApiException {
    public BookException(String message){super(HttpStatus.CONFLICT, message);}
}
