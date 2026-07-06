package com.yvl.vorstu.exception;

public class EmptyCsvFileException extends RuntimeException {

    public EmptyCsvFileException() {
        super("CSV file is empty");
    }
}