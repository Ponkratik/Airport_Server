package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;

public interface TeamService {
    boolean createTeam(int flightID, List<User> teamMembers) throws ServiceException;
    List<User> findTeam(int flightID) throws ServiceException;
    boolean updateTeam(int flightID, List<User> teamMembers) throws ServiceException;
    boolean deleteTeam(int flightID) throws ServiceException;
}
