package com.book_heaven.backend.refreshToken;

import com.book_heaven.backend.exception.RefreshTokenException;
import com.book_heaven.backend.refreshToken.dto.response.TokenRefreshResponse;
import com.book_heaven.backend.security.jwt.JwtUtils;
import com.book_heaven.backend.user.User;
import com.book_heaven.backend.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${backend.app.jwtRefreshExpirationMs}")
    private Long refreshTokenExpiration;

    @Value("${backend.app.jwtRefreshCookieName}")
    private String refreshTokenCookieName;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public TokenRefreshResponse checkRefreshToken(String refreshToken) {

        return findByToken(refreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    List<String> roles = user.getRoles().stream()
                            .map(role -> String.valueOf(role.getName()))
                            .toList();
                    return new TokenRefreshResponse(token, roles);
                })
                .orElseThrow(() -> new RefreshTokenException(refreshToken, "Refresh token is not in database"));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (Objects.requireNonNull(token.getExpiredDate()).compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getToken(), "Refresh token was expired. Please make a new sign-in request");
        }
        return token;
    }

    @Transactional
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username)));
    }

    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenCookieName, "")
                .path("/api")
                .maxAge(Duration.ZERO)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie generateRefreshTokenCookie(Long idUser) {
        String token = createRefreshToken(idUser);
        return ResponseCookie.from(refreshTokenCookieName, token)
                .path("/api")
                .maxAge(Duration.ofMillis(refreshTokenExpiration))
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();
    }

    private String createRefreshToken(Long idUser) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idUser));

        refreshToken.setUser(user);
        refreshToken.setExpiredDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }
}
