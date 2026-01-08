package com.book_heaven.backend.authenticate.dto.request;

public record SignUpRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String repeatedPassword) {
}
