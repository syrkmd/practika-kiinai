package com.yvl.vorstu.exception;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String role) {
        super("Unknown role: " + role);
    }
}