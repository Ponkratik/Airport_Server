package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.TeamDao;
import com.ponkratov.airport.server.model.dao.impl.TeamDaoImpl;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.TeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TeamServiceImpl implements TeamService {
    private static final Logger LOG = LogManager.getLogger();
    private static final TeamServiceImpl instance = new TeamServiceImpl();

    private TeamServiceImpl() {}

    public static TeamServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createTeam(int flightID, List<User> teamMembers) throws ServiceException {
        TeamDao dao = new TeamDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.createTeam(flightID, teamMembers);
        } catch (DaoException e) {
            LOG.error("Failed to create team, fligthID = " + flightID, e);
            throw new ServiceException("Failed to create team, fligthID = " + flightID, e);
        }
    }

    @Override
    public List<User> findTeam(int flightID) throws ServiceException {
        TeamDao dao = new TeamDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findTeam(flightID);
        } catch (DaoException e) {
            LOG.error("Failed to find team, fligthID = " + flightID, e);
            throw new ServiceException("Failed to find team, fligthID = " + flightID, e);
        }
    }

    @Override
    public boolean updateTeam(int flightID, List<User> teamMembers) throws ServiceException {
        TeamDao dao = new TeamDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            boolean isRemoved = dao.remove(flightID);
            boolean isCreated = dao.createTeam(flightID, teamMembers);
            return  isCreated;
        } catch (DaoException e) {
            LOG.error("Failed to update team, fligthID = " + flightID, e);
            throw new ServiceException("Failed to update team, fligthID = " + flightID, e);
        }
    }

    @Override
    public boolean deleteTeam(int flightID) throws ServiceException {
        TeamDao dao = new TeamDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.remove(flightID);
        } catch (DaoException e) {
            LOG.error("Failed to remove team, fligthID = " + flightID, e);
            throw new ServiceException("Failed to remove team, fligthID = " + flightID, e);
        }
    }
}
