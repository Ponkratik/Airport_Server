package com.ponkratov.airport.server.controller.util.validator;

public interface ParameterValidator {
    boolean validateUsername(String username);
    boolean validateFirstName(String firstName);
    boolean validateLastName(String lastName);
    boolean validateSurName(String surName);
    boolean validateEmail(String email);
    boolean validatePassword(String password);
    boolean validatePositiveInt(String number);
    boolean validateNameSearch(String searchRequest);
}
