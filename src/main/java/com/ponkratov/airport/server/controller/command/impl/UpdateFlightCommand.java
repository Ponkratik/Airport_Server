package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.sql.Timestamp;
import java.util.Map;

public class UpdateFlightCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        FlightService flightService = FlightServiceImpl.getInstance();

        int flightID = Integer.parseInt(params.get(RequestAttribute.FLIGHTID));
        Timestamp depTime = Timestamp.valueOf(params.get(RequestAttribute.DEPTIME));
        Timestamp arrTime = Timestamp.valueOf(params.get(RequestAttribute.ARRTIME));
        String IATACode = params.get(RequestAttribute.IATACODE);
        boolean isArrival = Boolean.parseBoolean(params.get(RequestAttribute.ISARRIVAL));
        int planeID = Integer.parseInt(params.get(RequestAttribute.FLIGHTID));
        int flightStatusID = Integer.parseInt(params.get(RequestAttribute.FLIGHTSTATUSID));

        Flight flight = new Flight.FlightBuilder()
                .setFlightID(flightID)
                .setDepTime(depTime)
                .setArrTime(arrTime)
                .setIATACode(IATACode)
                .setArrival(isArrival)
                .setPlaneID(planeID)
                .setFlightStatusID(flightStatusID)
                .createFlight();
        try {
            boolean isUpdated = flightService.updateFlight(flightID, flight);
            if (isUpdated) {
                return new CommandResult(ResponseStatus.OK, "Данные рейса обновлены", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные рейса", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to update flight, flightID = " + flightID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить рейс (вызвано исключение)", null);
        }
    }
}
