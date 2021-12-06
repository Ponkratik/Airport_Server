package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.Flight;

import java.sql.Timestamp;
import java.util.List;

public abstract class FlightDao extends BaseDao<Integer, Flight> {
    public abstract boolean create(Timestamp depTime, Timestamp arrTime, String IATACode, boolean isArrival, int planeID, int flightStatusID) throws DaoException;
    public abstract boolean updateStatus(int flightID, int newStatus) throws DaoException;
    public abstract boolean updatePlane(int flightID, int newPlane) throws DaoException;
    public abstract List<Flight> findAll() throws DaoException;
    public abstract List<Flight> findDepArrFlights(boolean isArr) throws DaoException;
}
