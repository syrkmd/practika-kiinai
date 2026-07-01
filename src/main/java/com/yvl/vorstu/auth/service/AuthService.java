package com.yvl.vorstu.auth.service;

import com.yvl.vorstu.auth.dto.request.LoginRequest;
import com.yvl.vorstu.auth.dto.response.LoginResponse;
import com.yvl.vorstu.dto.refreshToken.request.RefreshTokenRequest;
import com.yvl.vorstu.dto.refreshToken.response.RefreshTokenResponse;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.security.jwt.JwtService;
import com.yvl.vorstu.security.user.UserPrincipal;
import com.yvl.vorstu.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new IllegalStateException("Authenticated principal is invalid");
        }

        String accessToken = jwtService.generateAccessToken(userPrincipal);

        String refreshToken = jwtService.generateRefreshToken(userPrincipal);

        refreshTokenService.save(userPrincipal.getUser(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        User user = refreshTokenService.validate(request.getRefreshToken());

        refreshTokenService.deleteAll(user);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateAccessToken(userPrincipal);
        String refreshToken = jwtService.generateRefreshToken(userPrincipal);

        refreshTokenService.save(user, refreshToken);

        return new RefreshTokenResponse(accessToken, refreshToken);
    }
}
