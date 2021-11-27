package com.ponkratov.airport.server.model.entity;

public class FlightStatus implements Entity {
    private int flightStatusID;
    private String statusName;

    public FlightStatus(int flightStatusID, String statusName) {
        this.flightStatusID = flightStatusID;
        this.statusName = statusName;
    }

    public int getFlightStatusID() {
        return flightStatusID;
    }

    public String getStatusName() {
        return statusName;
    }
}
