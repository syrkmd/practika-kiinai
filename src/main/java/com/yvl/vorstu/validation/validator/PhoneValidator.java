package com.yvl.vorstu.validation.validator;

import com.yvl.vorstu.validation.annotation.ValidPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final String REGEX = "^(\\+7|8)\\d{10}$";;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        return value.matches(REGEX);
    }
}
