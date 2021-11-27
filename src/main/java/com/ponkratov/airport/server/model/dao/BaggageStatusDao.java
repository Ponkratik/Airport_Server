package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.BaggageStatus;

import java.util.Optional;

public abstract class BaggageStatusDao extends BaseDao<Integer, BaggageStatus> {
    public abstract Optional<BaggageStatus> findByStausName(String statusName) throws DaoException;
}
