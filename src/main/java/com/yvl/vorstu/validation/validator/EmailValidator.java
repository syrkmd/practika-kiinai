package com.yvl.vorstu.validation.validator;

import com.yvl.vorstu.services.EmailValidationService;
import com.yvl.vorstu.validation.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final EmailValidationService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return service.isValid(value);
    }
}
