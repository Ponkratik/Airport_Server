package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.controller.userhandler.UserThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        UserThread.setUserID(-1);
        UserThread.setRoleID(-1);
        return new CommandResult(ResponseStatus.OK, "Пользователь вышел из системы", null);
    }
}
