package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class UserDao extends BaseDao<Integer, User> {
    public abstract Optional<User> findByLogin(String username) throws DaoException;

    public abstract Optional<User> findByEmail(String email) throws DaoException;

    public abstract List<User> findByRole(int roleID) throws DaoException;

    public abstract List<User> findByLoginRegexp(String regexp) throws DaoException;

    public abstract boolean create(String login, String password, String email, int roleID) throws DaoException;

    public abstract String getPassword(int userID) throws DaoException;

    public abstract boolean updatePassword(int toUpdateID, String newPassword) throws DaoException;

    public abstract boolean updateRole(int toUpdateId, int newRole) throws DaoException;
}
