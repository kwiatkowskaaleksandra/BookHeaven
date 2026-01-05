package com.book_heaven.backend.authenticate.dto.response;

public record AuthResponseWithTokens(
        AuthResponse authResponse,
        String jwtToken,
        String refreshToken
) {
}
