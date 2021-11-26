package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.Entity;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<K, T extends Entity> {
    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> findById(K ID) throws DaoException;

    public abstract boolean remove(K ID) throws DaoException;

    public abstract boolean update(K ID, T replacement) throws DaoException;

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }
}
