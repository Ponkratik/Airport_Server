package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.UserDao;
import com.ponkratov.airport.server.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends UserDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT userID,
            login,
            email,
            user.roleID
            FROM user;
            """;

    private static final String SQL_CREATE_USER = """
            INSERT INTO user(login,
            password,
            email,
            lastName,
            firstName,
            surName,
            user.roleID)
            VALUES
            (?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String SQL_GET_PASSWORD = """
            SELECT password
            FROM user
            WHERE userID = ?;
            """;

    private static final String SQL_UPDATE_PASSWORD = """
            UPDATE user
            SET password = ?
            WHERE userID = ?
            """;

    private static final String SQL_RESTORE_PASSWORD = """
            UPDATE user
            SET password = ?
            WHERE userID = ?
            """;

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                int roleID = resultSet.getInt(4);

                /*User user = new User.UserBuilder()
                        .setUserID(userID)
                        .setLogin(login)
                        .setEmail(email)
                        .setRoleID(roleID)
                        .createUser();
                users.add(user);*/
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return users;
    }

    @Override
    public Optional<User> findById(Integer ID) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        return false;
    }

    @Override
    public User update(Integer ID, User replacement) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findByLogin(String username) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<User> findByRole(int roleID) throws DaoException {
        return null;
    }

    @Override
    public List<User> findByLoginRegexp(String regexp) throws DaoException {
        return null;
    }

    @Override
    public boolean create(String login, String password, String email, String lastName, String firstName, String surName, int roleID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, lastName);
            statement.setString(5, firstName);
            statement.setString(6, surName);
            statement.setInt(7, roleID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_CREATE_USER", e);
            throw new DaoException("Failed to execute SQL_CREATE_USER", e);
        }
    }


    @Override
    public String getPassword(int userID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_PASSWORD)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                throw new DaoException("User with id " + userID + " not found");
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_GET_PASSWORD", e);
            throw new DaoException("Failed to execute SQL_GET_PASSWORD", e);
        }
    }

    @Override
    public boolean updatePassword(int toUpdateID, String newPassword) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setInt(2, toUpdateID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_PASSWORD, userID = " + toUpdateID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_PASSWORD, userID = " + toUpdateID, e);
        }
    }

    @Override
    public boolean restorePassword(int userID, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_RESTORE_PASSWORD)) {
            statement.setString(1, password);
            statement.setInt(2, userID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_RESTORE_PASSWORD, userID = " + userID, e);
            throw new DaoException("Failed to execute SQL_RESTORE_PASSWORD, userID = " + userID, e);
        }
    }

    @Override
    public boolean updateRole(int toUpdateId, int newRole) throws DaoException {
        return false;
    }
}
