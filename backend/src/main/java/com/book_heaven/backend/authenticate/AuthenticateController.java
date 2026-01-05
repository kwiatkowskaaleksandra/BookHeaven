package com.book_heaven.backend.authenticate;

import com.book_heaven.backend.authenticate.dto.request.LoginRequest;
import com.book_heaven.backend.authenticate.dto.request.SignUpRequest;
import com.book_heaven.backend.authenticate.dto.response.AuthResponseWithTokens;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthenticateController {

    private final AuthenticateService authenticateService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authenticateService.addNewUser(signUpRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponseWithTokens authResponse = authenticateService.authenticate(loginRequest);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authResponse.jwtToken(), authResponse.refreshToken())
                .body(authResponse.authResponse());
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout() {
        String[] cookies = authenticateService.logout().stream()
                .map(Object::toString)
                .toArray(String[]::new);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookies)
                .body(Map.of("message", "You've been signed out."));
    }
}
