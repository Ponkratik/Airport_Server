package com.ponkratov.airport.server.model.entity;

public class Airport implements Entity {
    private String IATACode;
    private String country;
    private String city;

    public Airport(String IATACode, String country, String city) {
        this.IATACode = IATACode;
        this.country = country;
        this.city = city;
    }

    public String getIATACode() {
        return IATACode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
