package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogoutCommandTest {

    @Test
    void execute() throws JsonProcessingException {
        Map<String, String> params = new HashMap<>();
        Assertions.assertEquals("OK", new LogoutCommand().execute(params).getResponseStatus());
    }
}