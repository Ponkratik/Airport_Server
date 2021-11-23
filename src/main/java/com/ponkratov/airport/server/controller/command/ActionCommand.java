package com.ponkratov.airport.server.controller.command;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

@FunctionalInterface
public interface ActionCommand {
    CommandResult execute(Map<String, String> params) throws JsonProcessingException;
}
