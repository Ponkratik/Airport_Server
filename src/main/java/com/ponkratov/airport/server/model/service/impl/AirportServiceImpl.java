package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.AirportDao;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.impl.AirportDaoImpl;
import com.ponkratov.airport.server.model.entity.Airport;
import com.ponkratov.airport.server.model.service.AirportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class AirportServiceImpl implements AirportService {
    private static final Logger LOG = LogManager.getLogger();
    private static final AirportServiceImpl instance = new AirportServiceImpl();

    private AirportServiceImpl() {
    }

    public static AirportServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Airport> findAll() throws ServiceException {
        AirportDao dao = new AirportDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all airports.", e);
            throw new ServiceException("Failed to find all airports.", e);
        }
    }

    @Override
    public Optional<Airport> findByID(String IATACode) throws ServiceException {
        AirportDao dao = new AirportDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Airport> queryResult = dao.findById(IATACode);
            if (queryResult.isPresent()) {
                Airport airport = queryResult.get();
                return Optional.of(airport);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find airport by IATACode, IATACode = " + IATACode, e);
            throw new ServiceException("Failed to find airport by IATACode, IATACode = " + IATACode, e);
        }
    }

    @Override
    public boolean updateAirport(String IATACode, Airport replacement) throws ServiceException {
        AirportDao dao = new AirportDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.update(IATACode, replacement);
        } catch (DaoException e) {
            LOG.error("Failed to update airport by IATACode, IATACode = " + IATACode, e);
            throw new ServiceException("Failed to update airport by IATACode, IATACode = " + IATACode, e);
        }
    }
}
