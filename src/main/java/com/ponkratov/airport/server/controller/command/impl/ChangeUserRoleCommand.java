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

public class ChangeUserRoleCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        int userID = Integer.parseInt(params.get(RequestAttribute.USERID));
        int userRoleID = Integer.parseInt(params.get(RequestAttribute.USERROLEID));
        UserService service = UserServiceImpl.getInstance();
        try {
            boolean isExecuted = service.updateRole(userID, userRoleID);
            if (isExecuted) {
                return new CommandResult(ResponseStatus.OK, "Роль пользователя обновлена", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить роль пользователя", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to update user role, userID = " + userID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить роль пользователя (вызвано исключение)", null);
        }
    }
}
