package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.controller.userhandler.UserThread;
import com.ponkratov.airport.server.model.entity.User;

import java.util.Map;

public class AuthenticateUserCommand implements ActionCommand {
    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        String tempLogin = params.get(RequestAttribute.USERLOGIN);
        String tempPass = params.get(RequestAttribute.USERPASS);

        UserThread.setUserID(1);
        UserThread.setRoleID(1);

        User tempUser = new User.UserBuilder()
                .setUserID(1)
                .setLogin("dafaf")
                .setPassword("afafa")
                .setEmail("fafa@fafa.re")
                .setRoleID(2)
                .createUser();

        return new CommandResult(ResponseStatus.OK, "Успех!", new ObjectMapper().writeValueAsString(tempUser));
    }
}
