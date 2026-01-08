package com.book_heaven.backend.authenticate;

import com.book_heaven.backend.user.User;
import com.book_heaven.backend.user.UserService;
import com.book_heaven.backend.authenticate.dto.request.LoginRequest;
import com.book_heaven.backend.authenticate.dto.request.SignUpRequest;
import com.book_heaven.backend.authenticate.dto.response.AuthResponse;
import com.book_heaven.backend.authenticate.dto.response.AuthResponseWithTokens;
import com.book_heaven.backend.role.Role;
import com.book_heaven.backend.enums.RoleEnum;
import com.book_heaven.backend.role.RoleRepository;
import com.book_heaven.backend.security.jwt.JwtUtils;
import com.book_heaven.backend.refreshToken.RefreshTokenService;
import com.book_heaven.backend.security.service.UserDetailsImpl;
import com.book_heaven.backend.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PasswordValidator passwordValidator;

    @Override
    public AuthResponseWithTokens authenticate(LoginRequest loginRequest) {
        userService.ensureActiveUserWithTheUsernameExists(loginRequest.username());
        refreshTokenService.deleteByUsername(loginRequest.username());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwt = jwtUtils.generateJwtCookie(Objects.requireNonNull(userDetails));
        ResponseCookie refreshToken = refreshTokenService.generateRefreshTokenCookie(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        AuthResponse authResponse = new AuthResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );

        log.info("The user {} has logged in.", authentication.getName());
        return new AuthResponseWithTokens(authResponse, jwt.toString(), refreshToken.toString());
    }

    @Override
    public List<ResponseCookie> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        refreshTokenService.deleteByUsername(Objects.requireNonNull(authentication).getName());
        log.info("The user {} has logged out.", authentication.getName());

        return List.of(
                jwtUtils.getCleanJwtCookie(),
                refreshTokenService.getCleanRefreshTokenCookie()
        );
    }

    @Override
    public void addNewUser(SignUpRequest signUpRequest) {
        passwordValidator.passwordChecking(signUpRequest.password(), signUpRequest.repeatedPassword());
        userService.checkUserData(signUpRequest.username(), signUpRequest.email());
        userService.saveNewUser(mapSignUpRequestToUser(signUpRequest));
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user =  new User();
        user.setUsername(signUpRequest.username());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setFirstname(signUpRequest.firstname());
        user.setLastname(signUpRequest.lastname());
        user.setEmail(signUpRequest.email());
        //todo jak dodam maile to tu trzeba zmienić
        user.setActivity(true);
        //todo te role tez zeby nie byly na stale
Set<String> roles = new HashSet<>();
roles.add("mod");
        user.setRoles(assignRolesToUser(roles));

        log.info("New user created: {}", user);

        return user;
    }

    private Set<Role> assignRolesToUser(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();

        roleNames.forEach(role -> {
            switch (role) {
                case "admin" -> {
                    Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                }
                case "mod" -> {
                    Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    roles.add(modRole);
                }
                default -> {
                    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            }
        });

        return roles;
    }
}
