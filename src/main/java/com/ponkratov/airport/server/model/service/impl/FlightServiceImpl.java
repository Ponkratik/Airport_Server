package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.FlightDao;
import com.ponkratov.airport.server.model.dao.impl.FlightDaoImpl;
import com.ponkratov.airport.server.model.entity.Flight;
import com.ponkratov.airport.server.model.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class FlightServiceImpl implements FlightService {
    private static final Logger LOG = LogManager.getLogger();
    private static final FlightServiceImpl instance = new FlightServiceImpl();

    public FlightServiceImpl() {
    }

    public static FlightServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createFlight(Timestamp depTime, Timestamp arrTime, String IATACode, boolean isArrival, int planeID, int flightStatusID) throws ServiceException {
        FlightDao dao = new FlightDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.create(depTime, arrTime, IATACode, isArrival, planeID, flightStatusID);
        } catch (DaoException e) {
            LOG.error("Failed to create flight", e);
            throw new ServiceException("Failed to create flight", e);
        }
    }

    @Override
    public List<Flight> findAll() throws ServiceException {
        FlightDao dao = new FlightDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all fights", e);
            throw new ServiceException("Failed to find all fights", e);
        }
    }

    @Override
    public Optional<Flight> findByID(int flightID) throws ServiceException {
        FlightDao dao = new FlightDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Flight> queryResult = dao.findById(flightID);
            if (queryResult.isPresent()) {
                Flight flight = queryResult.get();
                return Optional.of(flight);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find fight by ID, flightID = " + flightID, e);
            throw new ServiceException("Failed to find fight by ID, flightID = " + flightID, e);
        }
    }

    @Override
    public boolean updateStatus(int flightID, int newStatus) throws ServiceException {
        FlightDao dao = new FlightDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.updateStatus(flightID, newStatus);
        } catch (DaoException e) {
            LOG.error("Failed to update flightStatus, flightID = " + flightID, e);
            throw new ServiceException("Failed to update flightStatus, flightID = " + flightID, e);
        }
    }

    @Override
    public boolean updatePlane(int flightID, int newPlane) throws ServiceException {
        FlightDao dao = new FlightDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.updatePlane(flightID, newPlane);
        } catch (DaoException e) {
            LOG.error("Failed to update plane on flight, flightID = " + flightID, e);
            throw new ServiceException("Failed to update plane on flight, flightID = " + flightID, e);
        }
    }
}
