package com.yvl.vorstu.services;

import com.yvl.vorstu.entities.Role;
import com.yvl.vorstu.entities.StudentGroup;
import com.yvl.vorstu.repositories.RegistrationInvitationRepository;
import com.yvl.vorstu.repositories.UserRepository;
import com.yvl.vorstu.security.hash.HashService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationInvitationService {

    private final RegistrationInvitationRepository repository;
    private final UserRepository userRepository;
    private final StudentGroupService groupService;
    private final HashService hashService;
    private final EmailValidationService emailValidationService;

    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSV file is empty");
        }

        parseCsv(file);
    }

    private void parseCsv(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser parser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .get()
                    .parse(reader);

            parser.stream().forEach(this::processRecord);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }

    private void processRecord(CSVRecord record) {
        String firstName = record.get("firstName");
        String lastName = record.get("lastName");
        String middleName = record.get("middleName");
        String email = record.get("email");
        String groupName = record.get("group");

        Role role = parseRole(record.get("role"));

        StudentGroup group = findGroup(role, record.get("group"));

        String token = generateToken();

        String tokenHash = hashService.sha256(token);

        Instant expiresAt = Instant.now().plus(Duration.ofDays(1));
    }

    private Role parseRole(String value) {
        try {
            return Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown role: " + value);
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

    private void validateRecord(String email, Role role, StudentGroup group) {
        if (!emailValidationService.isValid(email)) {
            throw new InvalidEmailException(email);
        }

        if (repository.existsByEmail(email)) {
            throw new RegistrationInvitationAlreadyExistsException(email);
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }
}

