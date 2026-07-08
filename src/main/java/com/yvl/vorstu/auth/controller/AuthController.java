package com.yvl.vorstu.auth.controller;

import com.yvl.vorstu.auth.dto.request.LoginRequest;
import com.yvl.vorstu.auth.dto.request.RegisterRequest;
import com.yvl.vorstu.auth.dto.response.LoginResponse;
import com.yvl.vorstu.auth.service.AuthService;
import com.yvl.vorstu.dto.refreshToken.request.RefreshTokenRequest;
import com.yvl.vorstu.dto.refreshToken.response.RefreshTokenResponse;
import com.yvl.vorstu.dto.registrationInvitation.response.RegistrationInvitationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return service.refresh(request);
    }

    @GetMapping("/register")
    public RegistrationInvitationResponse getInvitation(
            @RequestParam String token
    ) {
        return service.getRegistrationInvitation(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }
}
