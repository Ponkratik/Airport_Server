package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.FlightStatusDao;
import com.ponkratov.airport.server.model.entity.FlightStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightStatusDaoImpl extends FlightStatusDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT flightStatusID,
            statusName
            FROM `flightstatus`;
            """;

    private static final String SQL_FIND_ID = """
            SELECT statusName
            FROM `flightstatus`
            WHERE flightStatusID = ?;
            """;

    private static final String SQL_FIND_NAME = """
            SELECT flightStatusID
            FROM `flightstatus`
            WHERE statusName = ?;
            """;

    @Override
    public List<FlightStatus> findAll() throws DaoException {
        List<FlightStatus> flightStatuses = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int flightStatusID = resultSet.getInt(1);
                String statusName = resultSet.getString(2);
                FlightStatus status = new FlightStatus(flightStatusID, statusName);
                flightStatuses.add(status);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return flightStatuses;
    }

    @Override
    public Optional<FlightStatus> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String statusName = resultSet.getString(1);
                FlightStatus status = new FlightStatus(ID, statusName);
                return Optional.of(status);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID", e);
            throw new DaoException("Failed to execute SQL_FIND_ID", e);
        }
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Integer ID, FlightStatus replacement) throws DaoException {
        return false;
    }

    @Override
    public Optional<FlightStatus> findByStausName(String statusName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NAME)) {
            statement.setString(1, statusName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roleID = resultSet.getInt(1);
                FlightStatus flightStatus = new FlightStatus(roleID, statusName);
                return Optional.of(flightStatus);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_NAME", e);
            throw new DaoException("Failed to execute SQL_FIND_NAME", e);
        }
    }
}
