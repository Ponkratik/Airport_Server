package com.ponkratov.airport.server.controller.util.validator.impl;

import com.ponkratov.airport.server.controller.util.validator.ParameterValidator;

import java.util.Scanner;

import static com.ponkratov.airport.server.controller.util.validator.ParameterRegexp.*;

public class ParameterValidatorImpl implements ParameterValidator {
    private static final ParameterValidatorImpl instance = new ParameterValidatorImpl();

    private ParameterValidatorImpl() {}

    public static ParameterValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateUsername(String username) {
        return username != null && username.matches(USERNAME_REGEXP);
    }

    @Override
    public boolean validateFirstName(String firstName) {
        return firstName != null && firstName.matches(FIRST_LAST_SUR_NAME_REGEXP);
    }

    @Override
    public boolean validateLastName(String lastName) {
        return lastName != null && lastName.matches(FIRST_LAST_SUR_NAME_REGEXP);
    }

    @Override
    public boolean validateSurName(String surName) {
        return surName != null && surName.matches(FIRST_LAST_SUR_NAME_REGEXP);
    }

    @Override
    public boolean validateEmail(String email) {
        return email != null && email.contains("@") && (email.length() >= 3 && email.length() <= 255);
    }

    @Override
    public boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEXP);
    }

    @Override
    public boolean validatePositiveInt(String number) {
        if (number == null) {
            return false;
        }
        try (Scanner scanner = new Scanner(number)) {
            boolean isInt = scanner.hasNextInt();
            if (isInt) {
                int value = scanner.nextInt();
                return value > 0;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean validateNameSearch(String searchRequest) {
        return searchRequest != null && searchRequest.matches(FIRST_LAST_SUR_NAME_SEARCH_REGEXP);
    }
}
