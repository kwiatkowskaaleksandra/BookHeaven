package com.book_heaven.backend.refreshToken;

import com.book_heaven.backend.refreshToken.dto.response.TokenRefreshResponse;
import org.springframework.http.ResponseCookie;

import java.util.Optional;


public interface RefreshTokenService {
    TokenRefreshResponse checkRefreshToken(String refreshToken);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUsername(String username);
    ResponseCookie getCleanRefreshTokenCookie();
    ResponseCookie generateRefreshTokenCookie(Long idUser);

}
