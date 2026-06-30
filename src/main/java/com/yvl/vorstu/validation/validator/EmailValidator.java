package com.yvl.vorstu.validation.validator;

import com.yvl.vorstu.validation.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "mail.ru",
            "yandex.ru",
            "sstu.ru"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        if (!value.matches(REGEX)) return false;

        String domain = value
                .substring(value.lastIndexOf('@') + 1)
                .toLowerCase();

        return ALLOWED_DOMAINS.contains(domain);
    }
}
