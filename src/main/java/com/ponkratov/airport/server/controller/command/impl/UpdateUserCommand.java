package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class UpdateUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        int userID = Integer.parseInt(params.get(RequestAttribute.USERID));
        String userLogin = params.get(RequestAttribute.USERLOGIN);
        String userEmail = params.get(RequestAttribute.USEREMAIL);
        String userLastName = params.get(RequestAttribute.USERLASTNAME);
        String userFirstName = params.get(RequestAttribute.USERFIRSTNAME);
        String userSurName = params.get(RequestAttribute.USERSURNAME);
        boolean isBlocked = Boolean.parseBoolean(params.get(RequestAttribute.USERCURRENTBLOCK));
        int userRoleID = Integer.parseInt(params.get(RequestAttribute.USERROLEID));
        User user = new User.UserBuilder().setUserID(userID)
                .setLogin(userLogin)
                .setEmail(userEmail)
                .setLastName(userLastName)
                .setFirstName(userFirstName)
                .setSurName(userSurName)
                .setBlocked(isBlocked)
                .setRoleID(userRoleID)
                .createUser();
        UserService service = UserServiceImpl.getInstance();
        try {
            Optional<User> queryResult = service.findByLogin(userLogin);
            if (queryResult.isPresent()) {
                return new CommandResult(ResponseStatus.ERROR, "Пользователь с таким логином уже существует", null);
            } else {
                Optional<User> queryResult1 = service.findByEmail(userEmail);
                if (queryResult1.isPresent()) {
                    return new CommandResult(ResponseStatus.ERROR, "Пользователь с таким эл.адресом уже существует", null);
                } else {
                    boolean isExecuted = service.updateUser(userID, user);
                    if (isExecuted) {
                        return new CommandResult(ResponseStatus.OK, "Данные пользователя обновлены", null);
                    } else {
                        return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные пользователя", null);
                    }
                }
            }
        } catch (ServiceException e) {
            LOG.error("Failed to update user, userID = " + userID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные пользователя (вызвано исключение)", null);
        }
    }
}
