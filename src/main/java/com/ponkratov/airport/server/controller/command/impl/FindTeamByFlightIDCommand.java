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

import java.util.List;
import java.util.Map;

public class FindTeamByFlightIDCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        TeamService service = TeamServiceImpl.getInstance();
        List<User> teamMembers = null;
        try {
            int flightID = Integer.parseInt(params.get(RequestAttribute.FLIGHTID));
            teamMembers = service.findTeam(flightID);
        } catch (ServiceException e) {
            LOG.error("Failed to execute FindTeamByFlightIDCommand", e);
        }
        return new CommandResult(ResponseStatus.OK, "Успех", new ObjectMapper().writeValueAsString(teamMembers));
    }
}
