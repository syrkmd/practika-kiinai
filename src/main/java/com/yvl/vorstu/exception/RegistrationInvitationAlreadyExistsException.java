package com.yvl.vorstu.exception;

public class RegistrationInvitationAlreadyExistsException extends RuntimeException {

    public RegistrationInvitationAlreadyExistsException(String email) {
        super("Registration invitation already exists for email: " + email);
    }
}