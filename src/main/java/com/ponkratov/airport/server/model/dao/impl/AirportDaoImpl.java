package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.AirportDao;
import com.ponkratov.airport.server.model.entity.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportDaoImpl extends AirportDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT IATACode,
            country,
            city
            FROM airport;
            """;

    private static final String SQL_FIND_ID = """            
            SELECT country,
            city
            FROM airport
            WHERE IATACode = ?;
            """;

    private static final String SQL_UPDATE_ID = """
            UPDATE airport
            SET country = ?,
            city = ?
            WHERE IATACode = ?
            """;

    @Override
    public List<Airport> findAll() throws DaoException {
        List<Airport> airports = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String IATACode = resultSet.getString(1);
                String country = resultSet.getString(2);
                String city = resultSet.getString(3);
                Airport airport = new Airport(IATACode, country, city);
                airports.add(airport);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return airports;
    }

    @Override
    public Optional<Airport> findById(String ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setString(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String country = resultSet.getString(1);
                String city = resultSet.getString(2);
                Airport airport = new Airport(ID, country, city);
                return Optional.of(airport);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID", e);
            throw new DaoException("Failed to execute SQL_FIND_ID", e);
        }
    }

    @Override
    public boolean remove(String ID) throws DaoException {
        return false;
    }

    @Override
    public boolean update(String ID, Airport replacement) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ID)) {
            statement.setString(1, replacement.getCountry());
            statement.setString(2, replacement.getCity());
            statement.setString(3, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ID, IATACode = " + ID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ID, IATACode = " + ID, e);
        }
    }
}
