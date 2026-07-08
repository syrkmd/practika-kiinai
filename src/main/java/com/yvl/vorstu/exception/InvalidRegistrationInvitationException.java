package com.yvl.vorstu.exception;

public class InvalidRegistrationInvitationException extends RuntimeException {

    public InvalidRegistrationInvitationException() {
        super("Registration invitation is invalid");
    }
}