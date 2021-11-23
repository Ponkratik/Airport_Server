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

    private static final String SQL_GET_PASSWORD = """
            SELECT password
            FROM user
            where userID = ?;
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
    public boolean create(String login, String password, String email, int roleID) throws DaoException {
        return false;
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
        return false;
    }

    @Override
    public boolean updateRole(int toUpdateId, int newRole) throws DaoException {
        return false;
    }
}
