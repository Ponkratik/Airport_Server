package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Plane;

import java.util.List;
import java.util.Optional;

public abstract class PlaneDao extends BaseDao<Integer, Plane> {
    public abstract boolean createPlane(String planeModel, String planeNumber, Integer seatsQuantity) throws DaoException;
    public abstract List<Plane> findByModelRegexp(String regexp) throws DaoException;
    public abstract Optional<Plane> findByNumber(String number) throws DaoException;
}
