package com.yvl.vorstu.repositories;

import com.yvl.vorstu.entities.RegistrationInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationInvitationRepository extends JpaRepository<RegistrationInvitation, Long> {

    Optional<RegistrationInvitation> findByEmail(String email);

    Optional<RegistrationInvitation> findByTokenHash(String tokenHash);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
