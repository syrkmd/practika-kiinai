package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.InvitationEmailDto;
import com.yvl.vorstu.entities.RegistrationInvitation;
import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.event.RegistrationInvitationsCreatedEvent;
import com.yvl.vorstu.exception.*;
import com.yvl.vorstu.repositories.RegistrationInvitationRepository;
import com.yvl.vorstu.repositories.UserRepository;
import com.yvl.vorstu.security.hash.HashService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationInvitationService {

    private final RegistrationInvitationRepository repository;
    private final UserRepository userRepository;
    private final StudentGroupService groupService;
    private final HashService hashService;
    private final EmailValidationService emailValidationService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyCsvFileException();
        }

        List<InvitationEmailDto> invitationEmails = parseCsv(file);

        eventPublisher.publishEvent(new RegistrationInvitationsCreatedEvent(invitationEmails));
    }

    private List<InvitationEmailDto> parseCsv(MultipartFile file) {
        List<RegistrationInvitation> invitations = new ArrayList<>();
        List<InvitationEmailDto> invitationEmails = new ArrayList<>();
        Set<String> emailsInBatch = new HashSet<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .get()
                    .parse(reader);

            parser.stream().forEach(record -> processRecord(record, invitations, invitationEmails, emailsInBatch));
        } catch (IOException | IllegalArgumentException e) {
            throw new CsvProcessingException(e);
        }

        repository.saveAll(invitations);

        return invitationEmails;
    }

    private void processRecord(
            CSVRecord record,
            List<RegistrationInvitation> invitations,
            List<InvitationEmailDto> invitationEmails,
            Set<String> emailsInBatch
    ) {
        String firstName = record.get("firstName");
        String lastName = record.get("lastName");
        String middleName = record.get("middleName");
        String email = record.get("email");
        String groupName = record.get("group");

        Role role = parseRole(record.get("role"));

        StudentGroup group = findGroup(role, groupName);

        validateRecord(email, emailsInBatch);

        String token = generateToken();
        String tokenHash = hashService.sha256(token);
        Instant expiresAt = Instant.now().plus(Duration.ofDays(1));

        RegistrationInvitation invitation = createInvitation(
                firstName,
                lastName,
                middleName,
                email,
                role,
                group,
                tokenHash,
                expiresAt
        );

        invitations.add(invitation);
        invitationEmails.add(new InvitationEmailDto(email, token));
        emailsInBatch.add(email);
    }

    private Role parseRole(String value) {
        try {
            return Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException(value);
        }
    }

    private StudentGroup findGroup(Role role, String groupName) {

        if (role == Role.TEACHER) {
            return null;
        }

        return groupService.findGroupByName(groupName);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void validateRecord(String email, Set<String> emailsInBatch) {
        if (!emailValidationService.isValid(email)) {
            throw new InvalidEmailException(email);
        }

        if (emailsInBatch.contains(email)) {
            throw new RegistrationInvitationAlreadyExistsException(email);
        }

        if (repository.existsByEmail(email)) {
            throw new RegistrationInvitationAlreadyExistsException(email);
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    private RegistrationInvitation createInvitation(
            String firstName,
            String lastName,
            String middleName,
            String email,
            Role role,
            StudentGroup group,
            String tokenHash,
            Instant expiresAt
    ) {
        return RegistrationInvitation.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .email(email)
                .role(role)
                .group(group)
                .tokenHash(tokenHash)
                .expiresAt(expiresAt)
                .build();
    }
}