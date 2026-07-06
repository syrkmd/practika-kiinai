package com.yvl.vorstu.services;

import com.yvl.vorstu.exception.EmailSendingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.registration.base-url}")
    private String registrationBaseUrl;

    private static final String INVITATION_SUBJECT = "Registration invitation";

    public void sendInvitation(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(email);
        message.setSubject(INVITATION_SUBJECT);

        message.setText(buildInvitationMessage(token));

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendingException(e);
        }
    }

    private String buildInvitationMessage(String token) {
        return """
            Hello!

            You have been invited to register.

            Complete your registration using the link below:

            %s

            This link is valid for 24 hours.
            """.formatted(buildRegistrationLink(token));
    }

    private String buildRegistrationLink(String token) {
        return registrationBaseUrl + "/auth/register?token=" + token;
    }
}