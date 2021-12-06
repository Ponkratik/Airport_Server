package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Flight;
import com.ponkratov.airport.server.model.service.FlightService;
import com.ponkratov.airport.server.model.service.impl.FlightServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class FindDepArrFlightsCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        boolean isArr = Boolean.parseBoolean(params.get(RequestAttribute.ISARRIVAL));
        FlightService service = FlightServiceImpl.getInstance();
        List<Flight> flights = null;
        try {
            flights = service.findDepArrFlights(isArr);
        } catch (ServiceException e) {
            LOG.error("Failed to execute FindDepArrFlightsCommand", e);
        }
        return new CommandResult(ResponseStatus.OK, "Успех", new ObjectMapper().writeValueAsString(flights));
    }
}
