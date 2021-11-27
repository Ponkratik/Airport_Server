package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.PassengerDao;
import com.ponkratov.airport.server.model.dao.impl.PassengerDaoImpl;
import com.ponkratov.airport.server.model.entity.Passenger;
import com.ponkratov.airport.server.model.service.PassengerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PassengerServiceImpl implements PassengerService {
    private static final Logger LOG = LogManager.getLogger();
    private static final PassengerServiceImpl instance = new PassengerServiceImpl();

    private PassengerServiceImpl() {
    }

    public static PassengerServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createPassenger(String lastName, String firstName, String surName, String identificationNumber) throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.create(lastName, firstName, surName, identificationNumber);
        } catch (DaoException e) {
            LOG.error("Failed to create passenger, identifictionNumber = " + identificationNumber, e);
            throw new ServiceException("Failed to create passenger, identifictionNumber = " + identificationNumber, e);
        }
    }

    @Override
    public boolean updatePassenger(int passengerID, Passenger passenger) throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.update(passengerID, passenger);
        } catch (DaoException e) {
            LOG.error("Failed to update passenger, passengerID = " + passengerID, e);
            throw new ServiceException("Failed to update passenger, passengerID = " + passengerID, e);
        }
    }

    @Override
    public List<Passenger> findAll() throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all passengers.", e);
            throw new ServiceException("Failed to find all passengers.", e);
        }
    }

    @Override
    public Optional<Passenger> findByID(int passengerID) throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Passenger> queryResult = dao.findById(passengerID);
            if (queryResult.isPresent()) {
                Passenger passenger = queryResult.get();
                return Optional.of(passenger);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find passenger by ID, passengerID = " + passengerID, e);
            throw new ServiceException("Failed to find passenger by ID, passengerID = " + passengerID, e);
        }
    }

    @Override
    public List<Passenger> findByNameRegexp(String regexp) throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findByNameRegexp(regexp);
        } catch (DaoException e) {
            LOG.error("Failed to find all passengers by name.", e);
            throw new ServiceException("Failed to find all passengers by name.", e);
        }
    }

    @Override
    public Optional<Passenger> findByIdentificationNumber(String identificationNumber) throws ServiceException {
        PassengerDao dao = new PassengerDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Passenger> queryResult = dao.findByIdentificationNumber(identificationNumber);
            if (queryResult.isPresent()) {
                Passenger passenger = queryResult.get();
                return Optional.of(passenger);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find passenger by identificationNumber, identificationNumber = " + identificationNumber, e);
            throw new ServiceException("Failed to find passenger by identificationNumber, identificationNumber = " + identificationNumber, e);
        }
    }
}
