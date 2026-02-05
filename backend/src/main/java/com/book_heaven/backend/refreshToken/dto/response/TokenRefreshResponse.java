package com.book_heaven.backend.refreshToken.dto.response;

import java.util.List;

public record TokenRefreshResponse(String accessToken, List<String> roles) {
}
