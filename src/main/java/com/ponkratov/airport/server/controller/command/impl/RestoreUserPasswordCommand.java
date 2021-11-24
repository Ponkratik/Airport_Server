package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.controller.userhandler.UserThread;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RestoreUserPasswordCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        UserService userService = UserServiceImpl.getInstance();
        try {
            boolean isRestored = userService.restorePassword(UserThread.getUserID());
            if (isRestored) {
                return new CommandResult(ResponseStatus.OK, "Пароль сброшен успешно", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось сбросить пароль", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to restore password, userID = " + UserThread.getUserID(), e);
            return new CommandResult(ResponseStatus.ERROR, "Ошибка сброса пароля", null);
        }
    }
}
