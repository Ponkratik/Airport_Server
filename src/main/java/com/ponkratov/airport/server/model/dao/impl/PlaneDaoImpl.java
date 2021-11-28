package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.PlaneDao;
import com.ponkratov.airport.server.model.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaneDaoImpl extends PlaneDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT planeID,
            planeModel,
            planeNumber,
            seatsQuantity
            FROM plane;
            """;

    private static final String SQL_FIND_ID = """            
            SELECT planeModel,
            planeNumber,
            seatsQuantity
            FROM plane
            WHERE planeID = ?;
            """;

    private static final String SQL_UPDATE_ID = """
            UPDATE plane
            SET planeModel = ?,
            planeNumber = ?,
            seatsQuantity = ?
            WHERE planeID = ?
            """;

    @Override
    public List<Plane> findAll() throws DaoException {
        List<Plane> planes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int planeID = resultSet.getInt(1);
                String planeModel = resultSet.getString(2);
                String planeNumber = resultSet.getString(3);
                int seatsQuantity = resultSet.getInt(4);
                Plane plane = new Plane.PlaneBuilder().
                        setPlaneID(planeID).
                        setPlaneModel(planeModel).
                        setPlaneNumber(planeNumber).
                        setSeatsQuantity(seatsQuantity).
                        createPlane();
                planes.add(plane);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return planes;
    }

    @Override
    public Optional<Plane> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String planeModel = resultSet.getString(1);
                String planeNumber = resultSet.getString(2);
                int seatsQuantity = resultSet.getInt(3);
                Plane plane = new Plane.PlaneBuilder().
                        setPlaneID(ID).
                        setPlaneModel(planeModel).
                        setPlaneNumber(planeNumber).
                        setSeatsQuantity(seatsQuantity).
                        createPlane();
                return Optional.of(plane);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ID, planeID = " + ID, e);
            throw new DaoException("Failed to execute SQL_FIND_ID, planeID = " + ID, e);
        }
    }

    @Override
    public boolean remove(Integer ID) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Integer ID, Plane replacement) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ID)) {
            statement.setString(1, replacement.getPlaneModel());
            statement.setString(2, replacement.getPlaneNumber());
            statement.setInt(3, replacement.getSeatsQuantity());
            statement.setInt(4, ID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_UPDATE_ID, planeID = " + ID, e);
            throw new DaoException("Failed to execute SQL_UPDATE_ID, planeID = " + ID, e);
        }
    }
}
