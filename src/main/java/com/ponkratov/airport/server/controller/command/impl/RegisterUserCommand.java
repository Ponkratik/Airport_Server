package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RegisterUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        String userLogin = params.get(RequestAttribute.USERLOGIN);
        String userPass = "qwerty";
        String userEmail = params.get(RequestAttribute.USEREMAIL);
        String userLastName = params.get(RequestAttribute.USERLASTNAME);
        String userFirstName = params.get(RequestAttribute.USERFIRSTNAME);
        String userSurName = params.get(RequestAttribute.USERSURNAME);
        int userRoleID = Integer.parseInt(params.get(RequestAttribute.USERROLEID));

        UserService userService = UserServiceImpl.getInstance();
        try {
            boolean isCreated = userService.createUser(userLogin, userPass, userEmail, userLastName, userFirstName, userSurName, userRoleID);
            if (isCreated) {
                return new CommandResult(ResponseStatus.OK, "Пользователь успешно создан", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось создать пользователя", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to create user, userLogin = " + userLogin, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось создать пользователя (вызвано исключение)", null);
        }
    }
}
