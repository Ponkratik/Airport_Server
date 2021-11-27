package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.FlightStatus;

import java.util.List;
import java.util.Optional;

public interface FlightStatusService {
    List<FlightStatus> findAll() throws ServiceException;
    Optional<FlightStatus> findByID(int flightStatusID) throws ServiceException;
    Optional<FlightStatus> findByStatusName(String statusName) throws ServiceException;
}
