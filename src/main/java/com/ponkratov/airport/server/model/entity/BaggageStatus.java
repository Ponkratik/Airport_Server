package com.ponkratov.airport.server.model.entity;

public class BaggageStatus implements Entity {
    private int baggageStatusID;
    private String statusName;

    public BaggageStatus(int baggageStatusID, String statusName) {
        this.baggageStatusID = baggageStatusID;
        this.statusName = statusName;
    }

    public int getBaggageStatusID() {
        return baggageStatusID;
    }

    public String getStatusName() {
        return statusName;
    }
}
