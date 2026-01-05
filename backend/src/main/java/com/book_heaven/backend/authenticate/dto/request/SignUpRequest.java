package com.book_heaven.backend.authenticate.dto.request;

import java.util.Set;

public record SignUpRequest(
        String username,
        String firstname,
        String lastname,
        String email,
        String password,
        String repeatedPassword) {
}
