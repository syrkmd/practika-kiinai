package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.RefreshToken;
import com.yvl.vorstu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByJti(String jti);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findAllByUser(User user);
    
    void deleteAllByUser(User user);

}