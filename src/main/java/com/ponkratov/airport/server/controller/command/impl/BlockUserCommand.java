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

public class BlockUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        int userID = Integer.parseInt(params.get(RequestAttribute.USERID));
        boolean toBlock = !Boolean.parseBoolean(params.get(RequestAttribute.USERCURRENTBLOCK));
        UserService service = UserServiceImpl.getInstance();
        try {
            boolean isExecuted = service.block(userID, toBlock);
            if (isExecuted) {
                return new CommandResult(ResponseStatus.OK, "Пользователь успешно заблокирован/разблокирован", null);
            } else {
                return new CommandResult(ResponseStatus.OK, "Не удалось заблокировать/разблокировать этого пользователя", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to blok user, userID = " + userID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось заблокировать этого пользователя (вызвано исключение)", null);
        }
    }
}
