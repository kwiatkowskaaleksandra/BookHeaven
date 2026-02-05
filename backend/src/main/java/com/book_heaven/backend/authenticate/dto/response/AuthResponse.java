package com.book_heaven.backend.authenticate.dto.response;

import org.springframework.http.ResponseCookie;

import java.util.List;

public record AuthResponse(
        Long id,
        String username,
        String email,
        List<String> roles,
        ResponseCookie accessToken
) {
}
