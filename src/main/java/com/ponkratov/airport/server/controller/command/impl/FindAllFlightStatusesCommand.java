package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.FlightStatus;
import com.ponkratov.airport.server.model.service.FlightStatusService;
import com.ponkratov.airport.server.model.service.impl.FlightStatusServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class FindAllFlightStatusesCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        FlightStatusService service = FlightStatusServiceImpl.getInstance();
        List<FlightStatus> statuses = null;
        try {
            statuses = service.findAll();
        } catch (ServiceException e) {
            LOG.error("Failed to execute FindAllFlightStatusesCommand", e);
        }
        return new CommandResult(ResponseStatus.OK, "Успех", new ObjectMapper().writeValueAsString(statuses));
    }
}
