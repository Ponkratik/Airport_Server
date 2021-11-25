package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll() throws ServiceException;
    Optional<Role> findByID(int roleID) throws ServiceException;
    Optional<Role> findByRoleName(String roleName) throws ServiceException;
}
