package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.BaggageStatusDao;
import com.ponkratov.airport.server.model.entity.BaggageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaggageStatusDaoImpl extends BaggageStatusDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT baggageStatusID,
            statusName
            FROM `baggagestatus`;
            """;

    private static final String SQL_FIND_ID = """
            SELECT statusName
            FROM `baggagestatus`
            WHERE baggageStatusID = ?;
            """;

    private static final String SQL_FIND_NAME = """
            SELECT baggageStatusID
            FROM `baggagestatus`
            WHERE statusName = ?;
            """;
    
    @Override
    public Optional<BaggageStatus> findByStausName(String statusName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NAME)) {
            statement.setString(1, statusName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roleID = resultSet.getInt(1);
                BaggageStatus baggageStatus = new BaggageStatus(roleID, statusName);
                return Optional.of(baggageStatus);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_NAME", e);
            throw new DaoException("Failed to execute SQL_FIND_NAME", e);
        }
    }

    @Override
    public List<BaggageStatus> findAll() throws DaoException {
        List<BaggageStatus> baggageStatuses = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int flightStatusID = resultSet.getInt(1);
                String statusName = resultSet.getString(2);
                BaggageStatus status = new BaggageStatus(flightStatusID, statusName);
                baggageStatuses.add(status);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return baggageStatuses;
    }

    @Override
    public Optional<BaggageStatus> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String statusName = resultSet.getString(1);
                BaggageStatus status = new BaggageStatus(ID, statusName);
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
    public boolean update(Integer ID, BaggageStatus replacement) throws DaoException {
        return false;
    }
}
