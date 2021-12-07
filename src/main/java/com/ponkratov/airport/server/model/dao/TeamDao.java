package com.ponkratov.airport.server.model.dao;

import com.ponkratov.airport.server.exception.DaoException;
import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.Team;
import com.ponkratov.airport.server.model.entity.User;

import java.util.List;

public abstract class TeamDao extends BaseDao<Integer, Team> {
    public abstract boolean createTeam(int flightID, List<User> teamMembers) throws DaoException;
    public abstract List<User> findTeam(int flightID) throws DaoException;
}
