package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.Passenger;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;
import java.util.Optional;

public abstract class PassengerDao extends BaseDao<Integer, Passenger> {
    public abstract List<Passenger> findByNameRegexp(String regexp) throws DaoException;
    public abstract Optional<Passenger> findByIdentificationNumber(String identificationNumber) throws DaoException;
    public abstract boolean create(String lastName, String firstName, String surName, String identificationNumber) throws DaoException;
}
