package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.PassengerDao;
import com.ponkratov.airport.server.model.entity.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerDaoImpl extends PassengerDao {
    private static final Logger LOG = LogManager.getLogger();
    private static final String SQL_FIND_ALL = """
            SELECT passengerID,
            lastName,
            firstName,
            surName,
            identificationNumber
            FROM passenger;
            """;

    private static final String SQL_FIND_ID = """
            SELECT lastName,
            firstName,
            surName,
            identificationNumber
            FROM passenger
            WHERE passengerID = ?;
            """;

    private static final String SQL_UPDATE_ID = """
            UPDATE passenger
            SET lastName = ?,
            firstName = ?,
            surName = ?,
            identificationNumber = ?
            WHERE passengerID = ?;
            """;

    private static final String SQL_FIND_NAME_REGEXP = """
            SELECT passengerID,
            lastName,
            firstName,
            surName,
            identificationNumber
            FROM passenger
            WHERE CONCAT(lastName, ' ', firstName, ' ', surName) regexp ?;
            """;

    private static final String SQL_FIND_IDENTIFICATION = """
            """;

    private static final String SQL_CREATE_PASSENGER = """
            INSERT INTO passenger
            (lastName,
            firstName,
            surName,
            identificationNumber)
            VALUES
            (?, ?, ?, ?);
            """;

    @Override
    public List<Passenger> findAll() throws DaoException {
        List<Passenger> passengers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int passengerID = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String surName = resultSet.getString(4);
                String identificationNumber = resultSet.getString(5);

                Passenger passenger = new Passenger.PassengerBuilder().
                        setPassengerID(passengerID).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setIdentificationNumber(identificationNumber).
                        createPassenger();
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return passengers;
    }

    @Override
    public Optional<Passenger> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String lastName = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String surName = resultSet.getString(3);
                String identificationNumber = resultSet.getString(4);

                Passenger passenger = new Passenger.PassengerBuilder().
                        setPassengerID(ID).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setIdentificationNumber(identificationNumber).
                        createPassenger();
                return Optional.of(passenger);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID, passengerID = " + ID, e);
            throw new DaoException("Failed to execute SQL_FIND_ID, passengerID = " + ID, e);
        }
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Integer ID, Passenger replacement) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ID)) {
            statement.setString(1, replacement.getLastName());
            statement.setString(2, replacement.getFirstName());
            statement.setString(3, replacement.getSurName());
            statement.setString(4, replacement.getIdentificationNumber());
            statement.setInt(5, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ID, passengerID = " + ID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ID, passengerID = " + ID, e);
        }
    }

    @Override
    public List<Passenger> findByNameRegexp(String regexp) throws DaoException {
        List<Passenger> passengers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NAME_REGEXP)) {
            statement.setString(1, regexp);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int passengerID = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String surName = resultSet.getString(4);
                String identificationNumber = resultSet.getString(5);

                Passenger passenger = new Passenger.PassengerBuilder().
                        setPassengerID(passengerID).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setIdentificationNumber(identificationNumber).
                        createPassenger();
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_NAME_REGEXP", e);
            throw new DaoException("Failed to execute SQL_FIND_NAME_REGEXP", e);
        }

        return passengers;
    }

    @Override
    public Optional<Passenger> findByIdentificationNumber(String identificationNumber) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_IDENTIFICATION)) {
            statement.setString(1, identificationNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int passengerID = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String surName = resultSet.getString(4);
                Passenger passenger = new Passenger.PassengerBuilder().
                        setPassengerID(passengerID).
                        setLastName(lastName).
                        setFirstName(firstName).
                        setSurName(surName).
                        setIdentificationNumber(identificationNumber).
                        createPassenger();
                return Optional.of(passenger);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_IDENTIFICATION, identificationNumber = " + identificationNumber, e);
            throw new DaoException("Failed to execute SQL_FIND_IDENTIFICATION, identificationNumber = " + identificationNumber, e);
        }
    }

    @Override
    public boolean create(String lastName, String firstName, String surName, String identificationNumber) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PASSENGER)) {
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, surName);
            statement.setString(4, identificationNumber);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_CREATE_PASSENGER", e);
            throw new DaoException("Failed to execute SQL_CREATE_PASSENGER", e);
        }
    }
}
