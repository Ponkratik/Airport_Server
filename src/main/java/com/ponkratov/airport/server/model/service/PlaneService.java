package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Plane;

import java.util.List;
import java.util.Optional;

public interface PlaneService {
    List<Plane> findAll() throws ServiceException;
    Optional<Plane> findByID(Integer planeID) throws ServiceException;
    boolean updatePlane(Integer planeID, Plane replacement) throws ServiceException;
}
