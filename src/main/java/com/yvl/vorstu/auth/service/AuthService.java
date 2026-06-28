package com.yvl.vorstu.auth.service;

import com.yvl.vorstu.auth.dto.request.LoginRequest;
import com.yvl.vorstu.auth.dto.response.LoginResponse;
import com.yvl.vorstu.security.jwt.JwtService;
import com.yvl.vorstu.security.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

        String token = jwtService.generateToken(userPrincipal);

        return new LoginResponse(token);
    }

}
