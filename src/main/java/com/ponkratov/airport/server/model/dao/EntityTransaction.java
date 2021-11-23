package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.PoolException;
import com.ponkratov.airport.server.model.entity.Entity;
import com.ponkratov.airport.server.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class EntityTransaction implements AutoCloseable{
    private static final Logger LOG = LogManager.getLogger();
    private Connection connection;

    public void initAction(BaseDao<?, ? extends Entity> dao) throws DaoException {
        if (dao == null) {
            throw new DaoException("Passed parameters DAO is null, check arguments.");
        }
        if (connection == null) {
            CustomConnectionPool instance = CustomConnectionPool.getInstance();
            try {
                connection = instance.getConnection();
            } catch (PoolException e) {
                LOG.error("Failed to get connection from pool", e);
                throw new DaoException("Failed to get connection from pool", e);
            }
        }
        dao.setConnection(connection);
    }

    public void close() throws DaoException {
        if (connection == null) {
            throw new DaoException("Unable to close the connection which value is already null.");
        }
        CustomConnectionPool.getInstance().releaseConnection(connection);
        connection = null;
    }
}
