package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.BaggageStatusDao;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.impl.BaggageStatusDaoImpl;
import com.ponkratov.airport.server.model.entity.BaggageStatus;
import com.ponkratov.airport.server.model.service.BaggageStatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BaggageStatusServiceImpl implements BaggageStatusService {
    private static final Logger LOG = LogManager.getLogger();
    private static final BaggageStatusServiceImpl instance = new BaggageStatusServiceImpl();

    private BaggageStatusServiceImpl() {
    }

    @Override
    public List<BaggageStatus> findAll() throws ServiceException {
        BaggageStatusDao dao = new BaggageStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all baggage statuses", e);
            throw new ServiceException("Failed to find all baggage statuses", e);
        }
    }

    @Override
    public Optional<BaggageStatus> findByID(int baggageStatusID) throws ServiceException {
        BaggageStatusDao dao = new BaggageStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findById(baggageStatusID);
        } catch (DaoException e) {
            LOG.error("Failed to find baggageStatus by ID, baggageStatusID = " + baggageStatusID, e);
            throw new ServiceException("Failed to find baggageStatus by ID, baggageStatusID = " + baggageStatusID, e);
        }
    }

    @Override
    public Optional<BaggageStatus> findByStatusName(String statusName) throws ServiceException {
        BaggageStatusDao dao = new BaggageStatusDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findByStausName(statusName);
        } catch (DaoException e) {
            LOG.error("Failed to find baggageStatus by statusName, statusName = " + statusName, e);
            throw new ServiceException("Failed to find baggageStatus by statusName, statusName = " + statusName, e);
        }
    }
}
