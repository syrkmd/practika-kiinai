package com.yvl.vorstu.exception;

public class CsvProcessingException extends RuntimeException {

    public CsvProcessingException(Throwable cause) {
        super("Failed to process CSV file", cause);
    }
}