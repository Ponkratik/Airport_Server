package com.ponkratov.airport.server.model.service.impl;

import com.ponkratov.airport.server.controller.util.security.PasswordEncryptor;
import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.dao.EntityTransaction;
import com.ponkratov.airport.server.model.dao.UserDao;
import com.ponkratov.airport.server.model.dao.impl.UserDaoImpl;
import com.ponkratov.airport.server.model.entity.User;
import com.ponkratov.airport.server.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOG = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createUser(String login, String password, String email, String lastName, String firstName, String surName, int roleID) throws ServiceException {
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            boolean isCreated = dao.create(login, password, email, lastName, firstName, surName, roleID);
            return isCreated;
        } catch (DaoException e) {
            LOG.error("Failed to create user " + login, e);
            throw new ServiceException("Failed to create user " + login, e);
        }
    }


    @Override
    public Optional<User> authenticateByLogin(String login, String password) throws ServiceException{
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            Optional<User> queryResult = dao.findByLogin(login);
            if (queryResult.isPresent()) {
                User toAuthenticate = queryResult.get();
                String passFromDB = dao.getPassword(toAuthenticate.getUserID());
                String passToAuth = PasswordEncryptor.encrypt(password);
                return (passFromDB.equals(passToAuth)) ? queryResult : Optional.empty();
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            LOG.error("Failed to authenticate user " + login, e);
            throw new ServiceException("Failed to authenticate user " + login, e);
        }
    }

    @Override
    public boolean comparePassword(String password1, String password2) {
        return password1.equals(password2);
    }

    @Override
    public boolean approvePassword(int userID, String password) throws ServiceException {
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            String passFromDB = dao.getPassword(userID);
            String passToAuth = PasswordEncryptor.encrypt(password);
            return passFromDB.equals(passToAuth);
        } catch (DaoException e) {
            LOG.error("Failed to approve password, userID = " + userID, e);
            throw new ServiceException("Failed to approve password, userID = " + userID, e);
        }
    }

    @Override
    public boolean updatePassword(int userID, String password) throws ServiceException {
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            boolean isUpdated = dao.updatePassword(userID, password);
            return isUpdated;
        } catch (DaoException e) {
            LOG.error("Failed to change user password, userID = " + userID, e);
            throw new ServiceException("Failed to change user password, userID = " + userID, e);
        }
    }

    @Override
    public boolean restorePassword(int userID) throws ServiceException {
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            String newPassword = PasswordEncryptor.encrypt("qwerty");
            boolean isRestored = dao.restorePassword(userID, newPassword);
            return isRestored;
        } catch (DaoException e) {
            LOG.error("Failed to restore user password, userID = " + userID, e);
            throw new ServiceException("Failed to restore user password, userID = " + userID, e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDao dao = new UserDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.initAction(dao);
            return dao.findAll();
        } catch (DaoException e) {
            LOG.error("Failed to find all users.", e);
            throw new ServiceException("Failed to find all users.", e);
        }
    }
}
