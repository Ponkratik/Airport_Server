package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> createUser(String login, String password, String email, int roleID) throws ServiceException;

    List<User> findAll() throws ServiceException;
}
