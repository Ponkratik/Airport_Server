package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Flight;
import com.ponkratov.airport.server.model.entity.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    boolean createFlight(Timestamp depTime, Timestamp arrTime, String IATACode, boolean isArrival, int planeID, int flightStatusID) throws ServiceException;
    public List<Flight> findAll() throws ServiceException;
    public List<Flight> findDepArrFlights(boolean isArr) throws ServiceException;
    public Optional<Flight> findByID(int flightID) throws ServiceException;
    public boolean updateStatus(int flightID, int newStatus) throws ServiceException;
    public boolean updateFlight(int flightID, Flight replacement) throws ServiceException;
    public boolean updatePlane(int flightID, int newPlane) throws ServiceException;
    List<Flight> findFlightsByIdDate(int userID, String date) throws ServiceException;
}
