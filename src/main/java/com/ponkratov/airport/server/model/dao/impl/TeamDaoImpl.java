package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.TeamDao;
import com.ponkratov.airport.server.model.entity.Team;
import com.ponkratov.airport.server.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl extends TeamDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_TEAMMEMBERS = """
            SELECT t.userID,
            login,
            email,
            lastName,
            firstName,
            surName,
            isBlocked,
            roleID
            FROM team t
            INNER JOIN user u on t.userID = u.userID
            WHERE flightID = ?;
            """;

    private static final String SQL_CREATE_TEAM = """
            INSERT INTO team (flightID, userID)
            VALUES (?, ?);
            """;

    private static final String SQL_REMOVE_TEAM = """
            DELETE FROM team
            WHERE flightID = ?;
            """;

    @Override
    public List<Team> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Team> findById(Integer ID) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_TEAM)) {
            statement.setInt(1, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_REMOVE_TEAM, flightID = " + ID, e);
            throw new DaoException("Failed to execute SQL_REMOVE_TEAM, flightID = " + ID, e);
        }
    }

    @Override
    public boolean update(Integer ID, Team replacement) throws DaoException {
        return false;
    }

    @Override
    public boolean createTeam(int flightID, List<User> teamMembers) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_TEAM)) {
            int count = 0;
            for (User teamMember : teamMembers) {
                statement.setInt(1, flightID);
                statement.setInt(2, teamMember.getUserID());
                count += statement.executeUpdate();
            }

            return count > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_CREATE_TEAM, flightID = " + flightID, e);
            throw new DaoException("Failed to execute SQL_CREATE_TEAM, flightID = " + flightID, e);
        }
    }

    @Override
    public List<User> findTeam(int flightID) throws DaoException {
        List<User> teamMembers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_TEAMMEMBERS)) {
            statement.setInt(1, flightID);
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


                teamMembers.add(user);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_TEAMMEMBERS, flightID = " + flightID, e);
            throw new DaoException("Failed to execute SQL_FIND_TEAMMEMBERS, flightID = " + flightID, e);
        }

        return teamMembers;
    }
}
