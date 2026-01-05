package com.book_heaven.backend.authenticate;

import com.book_heaven.backend.authenticate.dto.request.LoginRequest;
import com.book_heaven.backend.authenticate.dto.request.SignUpRequest;
import com.book_heaven.backend.authenticate.dto.response.AuthResponseWithTokens;
import org.springframework.http.ResponseCookie;

import java.util.List;

public interface AuthenticateService {
    AuthResponseWithTokens authenticate(LoginRequest loginRequest);
    List<ResponseCookie> logout();
    void addNewUser(SignUpRequest signUpRequest);
}
