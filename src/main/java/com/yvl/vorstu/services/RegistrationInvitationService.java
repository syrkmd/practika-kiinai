package com.yvl.vorstu.services;

import com.yvl.vorstu.dto.registrationInvitation.InvitationEmailDto;
import com.yvl.vorstu.dto.registrationInvitation.RegistrationInvitationEmailPayload;
import com.yvl.vorstu.entities.OutboxEventType;
import com.yvl.vorstu.entities.RegistrationInvitation;
import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.exception.*;
import com.yvl.vorstu.repositories.RegistrationInvitationRepository;
import com.yvl.vorstu.repositories.UserRepository;
import com.yvl.vorstu.security.hash.HashService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.DuplicateHeaderMode;
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
    private final OutboxService outboxService;

    @Transactional
    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyCsvFileException();
        }

        List<InvitationEmailDto> invitationEmails = parseCsv(file);

        invitationEmails.forEach(this::publishOutboxEvent);
    }

    private void publishOutboxEvent(InvitationEmailDto invitationEmail) {
        outboxService.publish(
                OutboxEventType.REGISTRATION_INVITATION_EMAIL,
                new RegistrationInvitationEmailPayload(invitationEmail.getEmail(), invitationEmail.getToken())
        );
    }

    public RegistrationInvitation getByToken(String token) {
        String tokenHash = hashService.sha256(token);

        RegistrationInvitation invitation = repository.findByTokenHash(tokenHash)
                .orElseThrow(InvalidRegistrationInvitationException::new);

        if (invitation.getExpiresAt().isBefore(Instant.now())) {
            throw new RegistrationInvitationExpiredException();
        }

        return invitation;
    }

    public void delete(RegistrationInvitation invitation) {
        repository.delete(invitation);
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
                    .setDuplicateHeaderMode(DuplicateHeaderMode.DISALLOW)
                    .get()
                    .parse(reader);

            validateHeaders(parser);

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

        validateRequiredFields(firstName, lastName, email);

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
        Role role;

        try {
            role = Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException(value);
        }

        if (role == Role.ADMIN) {
            throw new InvalidRoleException(value);
        }

        return role;
    }

    private StudentGroup findGroup(Role role, String groupName) {

        if (role == Role.TEACHER) {
            return null;
        }

        if (groupName == null || groupName.isBlank()) {
            throw new InvalidCsvFieldException("Group is required for STUDENT role");
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

    private void validateHeaders(CSVParser parser) {

        Set<String> headers = parser.getHeaderMap().keySet();

        List<String> required = List.of(
                "firstName",
                "lastName",
                "middleName",
                "email",
                "role",
                "group"
        );

        if (!headers.containsAll(required) || headers.size() != required.size()) {
            throw new InvalidCsvHeaderException();
        }
    }

    private void validateRequiredFields(
            String firstName,
            String lastName,
            String email
    ) {
        if (firstName == null || firstName.isBlank()) {
            throw new InvalidCsvFieldException("First name is required");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new InvalidCsvFieldException("Last name is required");
        }

        if (email == null || email.isBlank()) {
            throw new InvalidCsvFieldException("Email is required");
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