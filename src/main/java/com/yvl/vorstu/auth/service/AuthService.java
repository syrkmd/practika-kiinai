package com.yvl.vorstu.auth.service;

import com.yvl.vorstu.auth.dto.request.LoginRequest;
import com.yvl.vorstu.auth.dto.request.RegisterRequest;
import com.yvl.vorstu.auth.dto.response.LoginResponse;
import com.yvl.vorstu.dto.refreshToken.request.RefreshTokenRequest;
import com.yvl.vorstu.dto.refreshToken.response.RefreshTokenResponse;
import com.yvl.vorstu.dto.registrationInvitation.response.RegistrationInvitationResponse;
import com.yvl.vorstu.entities.RegistrationInvitation;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.exception.InvalidRoleException;
import com.yvl.vorstu.exception.UserAlreadyExistsException;
import com.yvl.vorstu.exception.UsernameAlreadyExistsException;
import com.yvl.vorstu.repositories.UserRepository;
import com.yvl.vorstu.security.jwt.JwtService;
import com.yvl.vorstu.security.user.UserPrincipal;
import com.yvl.vorstu.services.RefreshTokenService;
import com.yvl.vorstu.services.RegistrationInvitationService;
import com.yvl.vorstu.services.StudentService;
import com.yvl.vorstu.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RegistrationInvitationService invitationService;
    private final UserRepository userRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;


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

    public RegistrationInvitationResponse getRegistrationInvitation(String token) {
        RegistrationInvitation invitation = invitationService.getByToken(token);

        return new RegistrationInvitationResponse(
                invitation.getFirstName(),
                invitation.getLastName(),
                invitation.getMiddleName(),
                invitation.getEmail(),
                invitation.getRole()
        );
    }

    public LoginResponse register(RegisterRequest request) {

        RegistrationInvitation invitation = invitationService.getByToken(request.getToken());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        if (userRepository.existsByEmail(invitation.getEmail())) {
            throw new UserAlreadyExistsException(invitation.getEmail());
        }

        User user = userRepository.save(
                User.builder()
                        .username(request.getUsername())
                        .email(invitation.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(invitation.getRole())
                        .build()
        );

        switch (invitation.getRole()) {
            case STUDENT ->
                    studentService.createFromInvitation(invitation, user);

            case TEACHER ->
                    teacherService.createFromInvitation(invitation, user);

            default ->
                    throw new InvalidRoleException(invitation.getRole().name());
        }

        invitationService.delete(invitation);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        String accessToken = jwtService.generateAccessToken(userPrincipal);

        String refreshToken = jwtService.generateRefreshToken(userPrincipal);

        refreshTokenService.save(user, refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }
}
