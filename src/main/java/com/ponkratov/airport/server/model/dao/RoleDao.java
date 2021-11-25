package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.model.entity.Role;

import java.util.List;
import java.util.Optional;

public abstract class RoleDao extends BaseDao<Integer, Role> {
    public abstract Optional<Role> findByRoleName(String roleName) throws DaoException;
}
