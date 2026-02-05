package com.book_heaven.backend.refreshToken;

import com.book_heaven.backend.refreshToken.dto.response.TokenRefreshResponse;
import com.book_heaven.backend.validator.ExtractCookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final ExtractCookie extractCookie;

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refresh = extractCookie.extract(request, "book_haven_refresh");

        TokenRefreshResponse resp = refreshTokenService.checkRefreshToken(refresh);
        return ResponseEntity.ok(resp);
    }
}
