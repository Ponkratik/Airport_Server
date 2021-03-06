package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.controller.userhandler.UserThread;
import com.ponkratov.airport.server.controller.util.validator.ParameterValidator;
import com.ponkratov.airport.server.controller.util.validator.impl.ParameterValidatorImpl;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.service.UserService;
import com.ponkratov.airport.server.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ChangeUserPasswordCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        String password = params.get(RequestAttribute.USERPASS);
        String passwordNew = params.get(RequestAttribute.USERPASSNEW);
        String passwordRe = params.get(RequestAttribute.USERPASSRE);

        ParameterValidator validator = ParameterValidatorImpl.getInstance();
        if (validator.validatePassword(password) && validator.validatePassword(passwordRe) && validator.validatePassword(passwordNew)) {
            UserService userService = UserServiceImpl.getInstance();
            try {
                boolean isApproved = userService.approvePassword(UserThread.getUserID(), password);
                if (isApproved) {
                    boolean isEquals = userService.comparePassword(passwordNew, passwordRe);
                    if (isEquals) {
                        boolean isExecuted = userService.updatePassword(UserThread.getUserID(), passwordNew);
                        if (isExecuted) {
                            return new CommandResult(ResponseStatus.OK, "???????????? ?????????????? ??????????????", null);
                        } else {
                            return new CommandResult(ResponseStatus.ERROR, "???????????? ?????????? ????????????", null);
                        }
                    } else {
                        return new CommandResult(ResponseStatus.ERROR, "?????????? ???????????? ???? ??????????????????", null);
                    }
                } else {
                    return new CommandResult(ResponseStatus.ERROR, "???????????? ???????????????? ?????????????? ????????????", null);
                }
            } catch (ServiceException e) {
                LOG.error("Password change failed", e);
                return new CommandResult(ResponseStatus.ERROR, "???????????? ?????????? ????????????", null);
            }
        } else {
            return new CommandResult(ResponseStatus.ERROR, "???????? ???? ?????????????? ???? ???????????????? ?????? ????????????????????", null);
        }
    }
}
