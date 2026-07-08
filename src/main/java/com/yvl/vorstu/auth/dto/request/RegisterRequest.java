package com.yvl.vorstu.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}