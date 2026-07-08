package com.yvl.vorstu.exception;

public class RegistrationInvitationExpiredException extends RuntimeException {

    public RegistrationInvitationExpiredException() {
        super("Registration invitation has expired");
    }
}