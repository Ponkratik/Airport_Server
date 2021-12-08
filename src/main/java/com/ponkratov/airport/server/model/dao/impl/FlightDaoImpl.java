package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.FlightDao;
import com.ponkratov.airport.server.model.entity.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDaoImpl extends FlightDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT flightID,
            depTime,
            arrTime,
            IATACode,
            isArrival,
            planeID,
            flightStatusID
            FROM flight;
            """;

    private static final String SQL_FIND_DEP_ARR = """
            SELECT flightID,
            depTime,
            arrTime,
            IATACode,
            isArrival,
            planeID,
            flightStatusID
            FROM flight
            WHERE isArrival = ?;
            """;

    private static final String SQL_FIND_ID = """
            SELECT depTime,
            arrTime,
            IATACode,
            isArrival,
            planeID,
            flightStatusID
            FROM flight
            WHERE flightID = ?;
            """;

    private static final String SQL_UPDATE_ID = """
            UPDATE flight
            SET depTime = ?,
            arrTime = ?,
            IATACode = ?,
            isArrival = ?,
            planeID = ?,
            flightStatusID = ?
            WHERE flightID = ?;
            """;

    private static final String SQL_CREATE = """
            INSERT INTO flight (depTime, arrTime, IATACode, isArrival, planeID, flightStatusID)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String SQL_FIND_BY_USERID = """
            select flight.flightID,
            depTime,
            arrTime,
            IATACode,
            isArrival,
            planeID,
            flightStatusID
            from team
            left join flight on team.flightID = flight.flightID
            where userID = ? and depTime regexp ?;
            """;

    private static final String SQL_UPDATE_STATUS = """
            UPDATE flight
            SET flightStatusID = ?
            WHERE flightID = ?;
            """;

    private static final String SQL_UPDATE_PLANE = """
            UPDATE flight
            SET planeID = ?
            WHERE flightID = ?;
            """;

    @Override
    public List<Flight> findAll() throws DaoException {
        List<Flight> flights = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int flightID = resultSet.getInt(1);
                Timestamp depTime = resultSet.getTimestamp(2);
                Timestamp arrTime = resultSet.getTimestamp(3);
                String IATACode = resultSet.getString(4);
                boolean isArrival = resultSet.getBoolean(5);
                int planeID = resultSet.getInt(6);
                int flightStatusID = resultSet.getInt(7);

                Flight flight = new Flight.FlightBuilder()
                        .setFlightID(flightID)
                        .setDepTime(depTime)
                        .setArrTime(arrTime)
                        .setIATACode(IATACode)
                        .setArrival(isArrival)
                        .setPlaneID(planeID)
                        .setFlightStatusID(flightStatusID)
                        .createFlight();

                flights.add(flight);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return flights;
    }

    @Override
    public List<Flight> findDepArrFlights(boolean isArr) throws DaoException {
        List<Flight> flights = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_DEP_ARR)) {
            statement.setBoolean(1, isArr);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int flightID = resultSet.getInt(1);
                Timestamp depTime = resultSet.getTimestamp(2);
                Timestamp arrTime = resultSet.getTimestamp(3);
                String IATACode = resultSet.getString(4);
                boolean isArrival = resultSet.getBoolean(5);
                int planeID = resultSet.getInt(6);
                int flightStatusID = resultSet.getInt(7);

                Flight flight = new Flight.FlightBuilder()
                        .setFlightID(flightID)
                        .setDepTime(depTime)
                        .setArrTime(arrTime)
                        .setIATACode(IATACode)
                        .setArrival(isArrival)
                        .setPlaneID(planeID)
                        .setFlightStatusID(flightStatusID)
                        .createFlight();

                flights.add(flight);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_DEP_ARR", e);
            throw new DaoException("Failed to execute SQL_FIND_DEP_ARR", e);
        }

        return flights;
    }

    @Override
    public List<Flight> findFlightsByIdDate(int userID, String date) throws DaoException {
        List<Flight> flights = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USERID)) {
            statement.setInt(1, userID);
            statement.setString(2, date);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int flightID = resultSet.getInt(1);
                Timestamp depTime = resultSet.getTimestamp(2);
                Timestamp arrTime = resultSet.getTimestamp(3);
                String IATACode = resultSet.getString(4);
                boolean isArrival = resultSet.getBoolean(5);
                int planeID = resultSet.getInt(6);
                int flightStatusID = resultSet.getInt(7);

                Flight flight = new Flight.FlightBuilder()
                        .setFlightID(flightID)
                        .setDepTime(depTime)
                        .setArrTime(arrTime)
                        .setIATACode(IATACode)
                        .setArrival(isArrival)
                        .setPlaneID(planeID)
                        .setFlightStatusID(flightStatusID)
                        .createFlight();

                flights.add(flight);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_BY_USER_ID, userID = " + userID, e);
            throw new DaoException("Failed to execute SQL_FIND_BY_USER_ID, userID = " + userID, e);
        }

        return flights;
    }

    @Override
    public Optional<Flight> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Timestamp depTime = resultSet.getTimestamp(1);
                Timestamp arrTime = resultSet.getTimestamp(2);
                String IATACode = resultSet.getString(3);
                boolean isArrival = resultSet.getBoolean(4);
                int planeID = resultSet.getInt(5);
                int flightStatusID = resultSet.getInt(6);

                Flight flight = new Flight.FlightBuilder()
                        .setFlightID(ID)
                        .setDepTime(arrTime)
                        .setArrTime(arrTime)
                        .setIATACode(IATACode)
                        .setArrival(isArrival)
                        .setPlaneID(planeID)
                        .setFlightStatusID(flightStatusID)
                        .createFlight();
                return Optional.of(flight);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID, flightID = " + ID, e);
            throw new DaoException("Failed to execute SQL_FIND_ID, flightID = " + ID, e);
        }
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Integer ID, Flight replacement) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ID)) {
            statement.setTimestamp(1, replacement.getDepTime());
            statement.setTimestamp(2, replacement.getArrTime());
            statement.setString(3, replacement.getIATACode());
            statement.setBoolean(4, replacement.isArrival());
            statement.setInt(5, replacement.getPlaneID());
            statement.setInt(6, replacement.getFlightStatusID());
            statement.setInt(7, ID);
            return statement.executeUpdate() > 0;
        }  catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ID, flightID = " + ID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ID, flightID = " + ID, e);
        }
    }

    @Override
    public boolean create(Timestamp depTime, Timestamp arrTime, String IATACode, boolean isArrival, int planeID, int flightStatusID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setTimestamp(1, depTime);
            statement.setTimestamp(2, arrTime);
            statement.setString(3, IATACode);
            statement.setBoolean(4, isArrival);
            statement.setInt(5, planeID);
            statement.setInt(6, flightStatusID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_CREATE", e);
            throw new DaoException("Failed to execute SQL_CREATE", e);
        }
    }

    @Override
    public boolean updateStatus(int flightID, int newStatus) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            statement.setInt(1, newStatus);
            statement.setInt(2, flightID);
            return statement.executeUpdate() > 0;
        }  catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_STATUS, flightID = " + flightID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_STATUS, flightID = " + flightID, e);
        }
    }

    @Override
    public boolean updatePlane(int flightID, int newPlane) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PLANE)) {
            statement.setInt(1, newPlane);
            statement.setInt(2, flightID);
            return statement.executeUpdate() > 0;
        }  catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_PLANE, flightID = " + flightID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_PLANE, flightID = " + flightID, e);
        }
    }
}
