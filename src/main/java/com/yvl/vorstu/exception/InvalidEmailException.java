package com.yvl.vorstu.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String email) {
        super("Email is invalid: " + email);
    }
}