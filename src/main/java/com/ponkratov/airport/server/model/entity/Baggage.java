package com.ponkratov.airport.server.model.entity;

public class Baggage implements Entity {
    private int baggageID;
    private double weight;
    private int baggageStatusID;

    public Baggage(int baggageID, double weight, int baggageStatusID) {
        this.baggageID = baggageID;
        this.weight = weight;
        this.baggageStatusID = baggageStatusID;
    }

    public int getBaggageID() {
        return baggageID;
    }

    public double getWeight() {
        return weight;
    }

    public int getBaggageStatusID() {
        return baggageStatusID;
    }
}
