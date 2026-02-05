package com.book_heaven.backend.authenticate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record SignUpRequest(
        String username,
        String firstname,
        String lastname,
        @NotBlank
        @Email(message = "EMAIL_IS_INCORRECTLY_FORMATTED")
        String email,
        String password,
        String repeatedPassword,
        Set<String> roles) {
}
