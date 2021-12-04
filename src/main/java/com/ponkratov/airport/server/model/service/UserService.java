package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean createUser(String login, String password, String email, String lastName, String firstName, String surName, int roleID) throws ServiceException;
    boolean updateUser(int userID, User user) throws ServiceException;
    Optional<User> authenticateByLogin(String login, String password) throws ServiceException;
    boolean comparePassword(String password1, String password2);
    boolean approvePassword(int userID, String password) throws ServiceException;
    boolean updatePassword(int userID, String password) throws ServiceException;
    boolean restorePassword(int userID) throws ServiceException;
    boolean block(int userID, boolean toBlock) throws ServiceException;
    boolean updateRole(int userID, int newRole) throws ServiceException;
    List<User> findAll() throws ServiceException;
    Optional<User> findByEmail(String email) throws ServiceException;
    Optional<User> findByLogin(String login) throws ServiceException;
    List<User> findByRole(int roleID) throws ServiceException;
    List<User> findByLoginRegexp(String regexp) throws ServiceException;
    List<User> findByNameRegexp(String regexp) throws ServiceException;
}
