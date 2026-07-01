package com.yvl.vorstu.services;

import com.yvl.vorstu.entities.RefreshToken;
import com.yvl.vorstu.entities.User;
import com.yvl.vorstu.exception.InvalidRefreshTokenException;
import com.yvl.vorstu.repositories.RefreshTokenRepository;
import com.yvl.vorstu.security.hash.HashService;
import com.yvl.vorstu.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final HashService hashService;
    private final JwtService jwtService;

    public void save(User user, String refreshToken) {
        String jti = jwtService.extractJti(refreshToken);
        String tokenHash = hashService.sha256(refreshToken);

        RefreshToken token = RefreshToken.builder()
                .tokenHash(tokenHash)
                .jti(jti)
                .createdAt(Instant.now())
                .expiresAt(jwtService.extractExpiration(refreshToken))
                .revoked(false)
                .user(user)
                .build();

        repository.save(token);
    }

    public User validate(String refreshToken) {

        String jti = jwtService.extractJti(refreshToken);

        RefreshToken token = repository.findByJti(jti)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (token.isRevoked()) {
            throw new InvalidRefreshTokenException();
        }

        if (!token.getTokenHash()
                .equals(hashService.sha256(refreshToken))) {

            throw new InvalidRefreshTokenException();
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        return token.getUser();
    }

    public void deleteAll(User user) {
        repository.deleteAllByUser(user);
    }
}


