package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.UserDao;
import com.ponkratov.airport.server.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDaoImpl extends UserDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT userID,
            login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user;
            """;

    private static final String SQL_FIND_ID = """
            SELECT login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user
            WHERE userID = ?;
            """;

    private static final String SQL_FIND_LOGIN = """
            SELECT userID,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user
            WHERE login = ?;
            """;

    private static final String SQL_FIND_LOGIN_REGEXP = """
            SELECT userID,
            login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user
            WHERE login regexp ?;
            """;

    private static final String SQL_FIND_NAME_REGEXP = """
            SELECT userID,
            login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user
            WHERE CONCAT(lastName, ' ', firstName, ' ', surName) regexp ?;
            """;

    private static final String SQL_FIND_EMAIL = """
            SELECT userID,
            login,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID
            FROM user
            WHERE email = ?;
            """;

    private static final String SQL_FIND_ROLE = """
            SELECT userID,
            login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked
            FROM user
            WHERE user.roleID = ?;
            """;

    private static final String SQL_USERS_ROLES_COUNT = """
            SELECT roleName, count(userID)
            FROM user
            INNER JOIN role r on user.roleID = r.roleID
            GROUP BY user.roleID;
            """;

    private static final String SQL_CREATE_USER = """
            INSERT INTO user(login,
            password,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            user.roleID)
            VALUES
            (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String SQL_GET_PASSWORD = """
            SELECT password
            FROM user
            WHERE userID = ?;
            """;

    private static final String SQL_UPDATE_PASSWORD = """
            UPDATE user
            SET password = ?
            WHERE userID = ?;
            """;

    private static final String SQL_RESTORE_PASSWORD = """
            UPDATE user
            SET password = ?
            WHERE userID = ?;
            """;

    private static final String SQL_BLOCK_USER = """
            UPDATE user
            SET isBlocked = ?
            WHERE userID = ?;
            """;

    private static final String SQL_UPDATE_ROLE = """
            UPDATE user
            SET roleID = ?
            WHERE userID = ?;
            """;

    private static final String SQL_REMOVE_ID = """
            DELETE FROM user
            WHERE userID = ?
            """;

    private static final String SQL_UPDATE_ID = """
            UPDATE user
            SET login = ?,
            email = ?,
            lastName = ?,
            firstName = ?,
            surName = ?,
            isBlocked = ?,
            user.roleID = ?
            WHERE userID = ?;
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
                String lastName = resultSet.getString(4);
                String firstName = resultSet.getString(5);
                String surName = resultSet.getString(6);
                boolean isBlocked = resultSet.getBoolean(7);
                int roleID = resultSet.getInt(8);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return users;
    }

    @Override
    public Optional<User> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString(1);
                String email = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String surName = resultSet.getString(5);
                boolean isBlocked = resultSet.getBoolean(6);
                int roleID = resultSet.getInt(7);

                User user = new User.UserBuilder().
                        setUserID(ID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID, userID = " + ID, e);
            throw new DaoException("Failed to execute SQL_FIND_ID, userID = " + ID, e);
        }
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_ID)) {
            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_REMOVE_ID, userID = " + ID, e);
            throw new DaoException("Failed to execute SQL_REMOVE_ID, userID = " + ID, e);
        }
    }

    @Override
    public boolean update(Integer ID, User replacement) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ID)) {
            statement.setString(1, replacement.getLogin());
            statement.setString(2, replacement.getEmail());
            statement.setString(3, replacement.getLastName());
            statement.setString(4, replacement.getFirstName());
            statement.setString(5, replacement.getSurName());
            statement.setBoolean(6, replacement.isBlocked());
            statement.setInt(7, replacement.getRoleID());
            statement.setInt(8, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ID, userID = " + ID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ID, userID = " + ID, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String surName = resultSet.getString(5);
                boolean isBlocked = resultSet.getBoolean(6);
                int roleID = resultSet.getInt(7);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_LOGIN, login = " + login, e);
            throw new DaoException("Failed to execute SQL_FIND_LOGIN, login = " + login, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String surName = resultSet.getString(5);
                boolean isBlocked = resultSet.getBoolean(6);
                int roleID = resultSet.getInt(7);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_EMAIL, email = " + email, e);
            throw new DaoException("Failed to execute SQL_FIND_EMAIL, email = " + email, e);
        }
    }

    @Override
    public List<User> findByRole(int roleID) throws DaoException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE)) {
            statement.setInt(1, roleID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String firstName = resultSet.getString(5);
                String surName = resultSet.getString(6);
                boolean isBlocked = resultSet.getBoolean(7);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ROLE, roleID = " + roleID, e);
            throw new DaoException("Failed to execute SQL_FIND_ROLE, roleID = " + roleID, e);
        }
        return users;
    }

    @Override
    public List<User> findByLoginRegexp(String regexp) throws DaoException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LOGIN_REGEXP)) {
            statement.setString(1, regexp);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String firstName = resultSet.getString(5);
                String surName = resultSet.getString(6);
                boolean isBlocked = resultSet.getBoolean(7);
                int roleID = resultSet.getInt(8);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_LOGIN_REGEXP", e);
            throw new DaoException("Failed to execute SQL_FIND_LOGIN_REGEXP", e);
        }

        return users;
    }

    @Override
    public List<User> findByNameRegexp(String regexp) throws DaoException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NAME_REGEXP)) {
            statement.setString(1, regexp);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String firstName = resultSet.getString(5);
                String surName = resultSet.getString(6);
                boolean isBlocked = resultSet.getBoolean(7);
                int roleID = resultSet.getInt(8);

                User user = new User.UserBuilder().
                        setUserID(userID).
                        setLogin(login).
                        setEmail(email).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setBlocked(isBlocked).
                        setRoleID(roleID).
                        createUser();

                users.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_NAME_REGEXP", e);
            throw new DaoException("Failed to execute SQL_FIND_NAME_REGEXP", e);
        }

        return users;
    }

    @Override
    public Map<String, Integer> countUsersRoles() throws DaoException {
        Map<String, Integer> result = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_USERS_ROLES_COUNT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String roleName = resultSet.getString(1);
                int usersCount = resultSet.getInt(2);
                result.put(roleName, usersCount);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_USERS_ROLES_COUNT", e);
            throw new DaoException("Failed to execute SQL_USERS_ROLES_COUNT", e);
        }

        return result;
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
            statement.setBoolean(7, false);
            statement.setInt(8, roleID);
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
    public boolean block(int userID, boolean toBlock) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_BLOCK_USER)) {
            statement.setBoolean(1, toBlock);
            statement.setInt(2, userID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_BLOCK_USER, userID = " + userID, e);
            throw new DaoException("Failed to execute SQL_BLOCK_USER, userID = " + userID, e);
        }
    }

    @Override
    public boolean updateRole(int userID, int newRole) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ROLE)) {
            statement.setInt(1, newRole);
            statement.setInt(2, userID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ROLE, userID = " + userID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ROLE, userID = " + userID, e);
        }
    }
}
