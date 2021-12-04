package com.ponkratov.airport.server.controller.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class CreatePlaneCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        PlaneService planeService = PlaneServiceImpl.getInstance();
        String planeModel = params.get(RequestAttribute.PLANEMODEL);
        String planeNumber = params.get(RequestAttribute.PLANENUMBER);
        int seatsQuantity = Integer.parseInt(params.get(RequestAttribute.SEATSQUANTITY));
        try {
            Optional<Plane> queryResult = planeService.findByNumber(planeNumber);
            if (queryResult.isPresent()) {
                return new CommandResult(ResponseStatus.ERROR, "Самолёт с таким рег.номером уже существует", null);
            } else {
                boolean isCreated = planeService.createPlane(planeModel, planeNumber, seatsQuantity);
                if (isCreated) {
                    return new CommandResult(ResponseStatus.OK, "Самолёт успешно создан", null);
                } else {
                    return new CommandResult(ResponseStatus.ERROR, "Не удалось создать самолёт", null);
                }
            }
        } catch (ServiceException e) {
            LOG.error("Failed to create plane, planeNumber = " + planeNumber, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось создать самолёт (вызвано исключение)", null);
        }
    }
}
