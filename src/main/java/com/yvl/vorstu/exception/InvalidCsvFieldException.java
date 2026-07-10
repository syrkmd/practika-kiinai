package com.yvl.vorstu.exception;

public class InvalidCsvFieldException extends RuntimeException {

    public InvalidCsvFieldException(String message) {
        super(message);
    }
}