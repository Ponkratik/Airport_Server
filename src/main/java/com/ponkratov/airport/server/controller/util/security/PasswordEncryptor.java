package com.ponkratov.airport.server.controller.util.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    private static final String SALT = "$2a/4Ad$10$miuR6CbZazuYx/2x0u";

    private PasswordEncryptor() {}

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, SALT);
    }
}
