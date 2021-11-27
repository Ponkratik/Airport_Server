package com.ponkratov.airport.server.model.service;

import com.ponkratov.airport.server.exception.ServiceException;
import com.ponkratov.airport.server.model.entity.BaggageStatus;

import java.util.List;
import java.util.Optional;

public interface BaggageStatusService {
    List<BaggageStatus> findAll() throws ServiceException;
    Optional<BaggageStatus> findByID(int baggageStatusID) throws ServiceException;
    Optional<BaggageStatus> findByStatusName(String statusName) throws ServiceException;
}
