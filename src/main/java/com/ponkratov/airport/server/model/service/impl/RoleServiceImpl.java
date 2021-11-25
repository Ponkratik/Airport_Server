package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.RoleDao;
import com.ponkratov.airport.server.model.dao.impl.RoleDaoImpl;
import com.ponkratov.airport.server.model.entity.Role;
import com.ponkratov.airport.server.model.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    private static final Logger LOG = LogManager.getLogger();
    private static final RoleServiceImpl instance = new RoleServiceImpl();

    private RoleServiceImpl() {}

    public static RoleServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Role> findAll() throws ServiceException {
        RoleDao dao = new RoleDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all roles", e);
            throw new ServiceException("Failed to find all roles", e);
        }
    }

    @Override
    public Optional<Role> findByID(int roleID) throws ServiceException {
        RoleDao dao = new RoleDaoImpl();

        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findById(roleID);
        } catch (DaoException e) {
            LOG.error("Failed to find role by ID, roleID = " + roleID, e);
            throw new ServiceException("Failed to find role by ID, roleID = " + roleID, e);
        }
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) throws ServiceException {
        RoleDao dao = new RoleDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findByRoleName(roleName);
        } catch (DaoException e) {
            LOG.error("Failed to find role by Name, roleName = " + roleName, e);
            throw new ServiceException("Failed to find role by Name, roleName = " + roleName, e);
        }
    }
}
