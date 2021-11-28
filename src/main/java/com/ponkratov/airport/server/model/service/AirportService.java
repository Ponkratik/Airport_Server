package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> findAll() throws ServiceException;
    Optional<Airport> findByID(String IATACode) throws ServiceException;
    boolean updateAirport(String IATACode, Airport replacement) throws ServiceException;
}
