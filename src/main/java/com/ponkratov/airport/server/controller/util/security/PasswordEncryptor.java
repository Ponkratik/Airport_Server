package com.ponkratov.airport.server.controller.util.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    private static final String SALT = "$2a$10$miuR/4ZazuYx/2x0Ad6Cbu";

    private PasswordEncryptor() {}

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, SALT);
    }
}
