package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.controller.userhandler.UserThread;
import com.ponkratov.airport.server.controller.util.validator.ParameterValidator;
import com.ponkratov.airport.server.controller.util.validator.impl.ParameterValidatorImpl;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class AuthenticateUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        String login = params.get(RequestAttribute.USERLOGIN);
        String password = params.get(RequestAttribute.USERPASS);

        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        if (validator.validateLogin(login) && validator.validatePassword(password)) {
            UserService service = UserServiceImpl.getInstance();
            try {
                Optional<User> result = service.authenticateByLogin(login, password);
                if (result.isPresent()) {
                    User user = result.get();
                    UserThread.setUserID(user.getUserID());
                    UserThread.setRoleID(user.getRoleID());
                    return new CommandResult(ResponseStatus.OK, "Успех!", new ObjectMapper().writeValueAsString(user));
                } else {
                    return new CommandResult(ResponseStatus.ERROR, "Логин или пароль не верны", null);
                }
            } catch (ServiceException e) {
                LOG.error("Authentication failed", e);
                return new CommandResult(ResponseStatus.ERROR, "Ошибка аутентификации", null);
            }
        } else {
            return new CommandResult(ResponseStatus.ERROR, "Ошибка сервера", null);
        }
    }
}
