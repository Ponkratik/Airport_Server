package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.FlightStatusDao;
import com.ponkratov.airport.server.model.dao.impl.FlightStatusDaoImpl;
import com.ponkratov.airport.server.model.entity.FlightStatus;
import com.ponkratov.airport.server.model.service.FlightStatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FlightStatusServiceImpl implements FlightStatusService {
    private static final Logger LOG = LogManager.getLogger();
    private static final FlightStatusServiceImpl instance = new FlightStatusServiceImpl();

    private FlightStatusServiceImpl() {
    }

    private static FlightStatusServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<FlightStatus> findAll() throws ServiceException {
        FlightStatusDao dao = new FlightStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all flight statuses", e);
            throw new ServiceException("Failed to find all flight statuses", e);
        }
    }

    @Override
    public Optional<FlightStatus> findByID(int flightStatusID) throws ServiceException {
        FlightStatusDao dao = new FlightStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findById(flightStatusID);
        } catch (DaoException e) {
            LOG.error("Failed to find flightStatus by ID, flihgtStatusID = " + flightStatusID, e);
            throw new ServiceException("Failed to find flightStatus by ID, flihgtStatusID = " + flightStatusID, e);
        }
    }

    @Override
    public Optional<FlightStatus> findByStatusName(String statusName) throws ServiceException {
        FlightStatusDao dao = new FlightStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findByStausName(statusName);
        } catch (DaoException e) {
            LOG.error("Failed to find flightStatus by statusName, statusName = " + statusName, e);
            throw new ServiceException("Failed to find flightStatus by statusName, statusName = " + statusName, e);
        }
    }
}
