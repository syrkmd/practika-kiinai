package com.yvl.vorstu.exception;

public class InvalidCsvHeaderException extends RuntimeException {

    public InvalidCsvHeaderException() {
        super("CSV file has invalid headers");
    }
}