package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class FindAllUsersCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        UserService service = UserServiceImpl.getInstance();
        List<User> users = null;
        try {
            users = service.findAll();
        } catch (ServiceException e) {
            LOG.error("Failed to execute FindAllUsersCommand", e);
        }
        return new CommandResult(ResponseStatus.OK, "Успех", new ObjectMapper().writeValueAsString(users));
    }
}
