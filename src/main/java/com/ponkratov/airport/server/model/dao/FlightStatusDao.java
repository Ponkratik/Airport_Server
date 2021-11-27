package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.FlightStatus;

import java.util.Optional;

public abstract class FlightStatusDao extends BaseDao<Integer, FlightStatus> {
    public abstract Optional<FlightStatus> findByStausName(String statusName) throws DaoException;
}
