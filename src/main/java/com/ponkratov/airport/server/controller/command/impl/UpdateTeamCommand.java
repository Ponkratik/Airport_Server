package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.TeamService;
import com.ponkratov.airport.server.model.service.impl.TeamServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateTeamCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        TeamService service = TeamServiceImpl.getInstance();
        int flightID = Integer.parseInt(params.get(RequestAttribute.FLIGHTID));
        List<User> teamMembers = new ArrayList<>();
        User.UserBuilder[] temp1 = new ObjectMapper().readValue(params.get(RequestAttribute.TEAMMEMBERS), User.UserBuilder[].class);
        for (User.UserBuilder userBuilder: temp1) {
            User user = userBuilder.createUser();
            teamMembers.add(user);
        }

        try {
            boolean isExecuted = service.updateTeam(flightID, teamMembers);
            if (isExecuted) {
                return new CommandResult(ResponseStatus.OK, "Данные бригады обновлены", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные бригады", null);
            }
        }  catch (ServiceException e) {
            LOG.error("Failed to update team, flightID = " + flightID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные пользователя (вызвано исключение)", null);
        }
    }
}
