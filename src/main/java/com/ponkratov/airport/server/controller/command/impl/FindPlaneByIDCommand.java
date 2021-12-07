package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponkratov.airport.server.controller.command.ActionCommand;
import com.ponkratov.airport.server.controller.command.CommandResult;
import com.ponkratov.airport.server.controller.command.RequestAttribute;
import com.ponkratov.airport.server.controller.command.ResponseStatus;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Plane;
import com.ponkratov.airport.server.model.service.PlaneService;
import com.ponkratov.airport.server.model.service.impl.PlaneServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class FindPlaneByIDCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        PlaneService service = PlaneServiceImpl.getInstance();
        int planeID = Integer.parseInt(params.get(RequestAttribute.PLANEID));
        try {
            Optional<Plane> result = service.findByID(planeID);
            if (result.isPresent()) {
                Plane plane = result.get();
                return new CommandResult(ResponseStatus.OK, "Успех", new ObjectMapper().writeValueAsString(plane));
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Ошибка в получении самолёта", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to execute FindPlaneByIDCommand", e);
            return new CommandResult(ResponseStatus.ERROR, "Ошибка в получении самолёта (вызвано исключение)", null);
        }
    }
}
