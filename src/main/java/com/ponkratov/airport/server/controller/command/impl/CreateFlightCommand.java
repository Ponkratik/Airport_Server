package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.service.FlightService;
import com.ponkratov.airport.server.model.service.impl.FlightServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Map;

public class CreateFlightCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        FlightService flightService = FlightServiceImpl.getInstance();
        Timestamp depTime = Timestamp.valueOf(params.get(RequestAttribute.DEPTIME));
        Timestamp arrTime = Timestamp.valueOf(params.get(RequestAttribute.ARRTIME));
        String IATACode = params.get(RequestAttribute.IATACODE);
        boolean isArrival = Boolean.parseBoolean(params.get(RequestAttribute.ISARRIVAL));
        int planeID = Integer.parseInt(params.get(RequestAttribute.FLIGHTID));
        int flightStatusID = Integer.parseInt(params.get(RequestAttribute.FLIGHTSTATUSID));

        try {
            boolean isCreated = flightService.createFlight(depTime, arrTime, IATACode, isArrival, planeID, flightStatusID);
            if (isCreated) {
                return new CommandResult(ResponseStatus.OK, "Рейс успешно создан", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось создать рейс", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to create flight", e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось создать рейс (вызвано исключение)", null);
        }
    }
}
