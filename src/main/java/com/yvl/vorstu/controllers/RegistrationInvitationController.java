package com.yvl.vorstu.controllers;

import com.yvl.vorstu.services.RegistrationInvitationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upload(@RequestParam MultipartFile file) {
        service.upload(file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/resend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resend(@PathVariable Long id) {
        service.resend(id);
    }
}