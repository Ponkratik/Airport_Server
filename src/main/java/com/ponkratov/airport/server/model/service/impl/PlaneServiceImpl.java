package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.PlaneDao;
import com.ponkratov.airport.server.model.dao.impl.PlaneDaoImpl;
import com.ponkratov.airport.server.model.entity.Plane;
import com.ponkratov.airport.server.model.service.PlaneService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaneServiceImpl implements PlaneService {
    private static final Logger LOG = LogManager.getLogger();
    private static final PlaneServiceImpl instance = new PlaneServiceImpl();

    private PlaneServiceImpl() {
    }

    public static PlaneServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Plane> findAll() throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all planes.", e);
            throw new ServiceException("Failed to find all planes.", e);
        }
    }

    @Override
    public Optional<Plane> findByID(Integer planeID) throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Plane> queryResut = dao.findById(planeID);
            if (queryResut.isPresent()) {
                Plane plane = queryResut.get();
                return Optional.of(plane);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find plane by ID, planeID = " + planeID, e);
            throw new ServiceException("Failed to find plane by ID, planeID = " + planeID, e);
        }
    }

    @Override
    public boolean updatePlane(Integer planeID, Plane replacement) throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.update(planeID, replacement);
        } catch (DaoException e) {
            LOG.error("Failed to update plane by ID, planeID = " + planeID, e);
            throw new ServiceException("Failed to update plane by ID, planeID = " + planeID, e);
        }
    }

    @Override
    public boolean createPlane(String planeModel, String planeNumber, Integer seatsQuantity) throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.createPlane(planeModel, planeNumber, seatsQuantity);
        } catch (DaoException e) {
            LOG.error("Failed to create plane", e);
            throw new ServiceException("Failed to create plane", e);
        }
    }

    @Override
    public List<Plane> findByModelRegexp(String regexp) throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findByModelRegexp(regexp);
        } catch (DaoException e) {
            LOG.error("Failed to find plane by model, regexp = " + regexp, e);
            throw new ServiceException("Failed to find plane by model, regexp = " + regexp, e);
        }
    }

    @Override
    public Optional<Plane> findByNumber(String number) throws ServiceException {
        PlaneDao dao = new PlaneDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<Plane> queryResult = dao.findByNumber(number);
            if (queryResult.isPresent()) {
                Plane plane = queryResult.get();
                return Optional.of(plane);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to find plane by number.", e);
            throw new ServiceException("Failed to find plane by number.", e);
        }
    }
}
