package com.ponkratov.airport.server.model.dao.impl;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.dao.RoleDao;
import com.ponkratov.airport.server.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDaoImpl extends RoleDao {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SQL_FIND_ALL = """
            SELECT roleID,
            roleName
            FROM `role`;
            """;

    private static final String SQL_FIND_ID = """
            SELECT roleName
            FROM `role`
            WHERE roleID = ?;
            """;

    private static final String SQL_FIND_NAME = """
            SELECT roleID
            FROM `role`
            WHERE roleName = ?;
            """;

    @Override
    public List<Role> findAll() throws DaoException {
        List<Role> roles = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int roleID = resultSet.getInt(1);
                String roleName = resultSet.getString(2);
                Role role = new Role(roleID, roleName);
                roles.add(role);
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_ALL", e);
            throw new DaoException("Failed to execute SQL_FIND_ALL", e);
        }

        return roles;
    }

    @Override
    public Optional<Role> findById(Integer ID) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ID)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String roleName = resultSet.getString(1);
                Role role = new Role(ID, roleName);
                return Optional.of(role);
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
    public Role update(Integer ID, Role replacement) throws DaoException {
        return null;
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_NAME)) {
            statement.setString(1, roleName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roleID = resultSet.getInt(1);
                Role role = new Role(roleID, roleName);
                return Optional.of(role);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOG.error("Failed to execute SQL_FIND_NAME", e);
            throw new DaoException("Failed to execute SQL_FIND_NAME", e);
        }
    }
}
