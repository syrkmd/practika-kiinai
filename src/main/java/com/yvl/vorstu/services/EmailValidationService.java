package com.yvl.vorstu.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EmailValidationService {

    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "mail.ru",
            "yandex.ru",
            "sstu.ru",
            "gmail.com"
    );

    public boolean isValid(String value) {

        if (value == null) return true;

        if (!value.matches(REGEX)) return false;

        String domain = value
                .substring(value.lastIndexOf('@') + 1)
                .toLowerCase();

        return ALLOWED_DOMAINS.contains(domain);
    }
}