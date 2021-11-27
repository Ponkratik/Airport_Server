package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {
    boolean createPassenger(String lastName, String firstName, String surName, String identificationNumber) throws ServiceException;
    boolean updatePassenger(int passengerID, Passenger passenger) throws ServiceException;
    List<Passenger> findAll() throws ServiceException;
    Optional<Passenger> findByID(int passengerID) throws ServiceException;
    List<Passenger> findByNameRegexp(String regexp) throws ServiceException;
    Optional<Passenger> findByIdentificationNumber(String identificationNumber) throws ServiceException;
}
