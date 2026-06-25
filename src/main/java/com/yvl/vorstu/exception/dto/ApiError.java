package com.yvl.vorstu.exception.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();;
    }

    private final String code;

    private final String message;

    private final LocalDateTime timestamp;
}
