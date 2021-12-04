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

public class UpdatePlaneCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public CommandResult execute(Map<String, String> params) throws JsonProcessingException {
        PlaneService planeService = PlaneServiceImpl.getInstance();
        int planeID = Integer.parseInt(params.get(RequestAttribute.PLANEID));
        String planeModel = params.get(RequestAttribute.PLANEMODEL);
        String planeNumber = params.get(RequestAttribute.PLANENUMBER);
        int seatsQuantity = Integer.parseInt(params.get(RequestAttribute.SEATSQUANTITY));
        Plane plane = new Plane.PlaneBuilder()
                .setPlaneID(planeID)
                .setPlaneModel(planeModel)
                .setPlaneNumber(planeNumber)
                .setSeatsQuantity(seatsQuantity)
                .createPlane();
        try {
            boolean isUpdated = planeService.updatePlane(planeID, plane);
            if (isUpdated) {
                return new CommandResult(ResponseStatus.OK, "Данные самолёта обновлены", null);
            } else {
                return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить данные самолёта", null);
            }
        } catch (ServiceException e) {
            LOG.error("Failed to update plane, planeID = " + planeID, e);
            return new CommandResult(ResponseStatus.ERROR, "Не удалось обновить самолёт (вызвано исключение)", null);
        }
    }
}
