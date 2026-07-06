package com.yvl.vorstu.controllers;

import com.yvl.vorstu.services.RegistrationInvitationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/registration-invitations")
@RequiredArgsConstructor
public class RegistrationInvitationController {

    private final RegistrationInvitationService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upload(@RequestParam MultipartFile file) {
        service.upload(file);
    }
}