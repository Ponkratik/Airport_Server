package com.ponkratov.airport.server.model.entity;

import java.util.Objects;

public class Passenger implements Entity{
    private final int passengerID;
    private final String lastName;
    private final String firstName;
    private final String surName;
    private final String identificationNumber;

    private Passenger(int passengerID, String lastName, String firstName, String surName, String identificationNumber) {
        this.passengerID = passengerID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.surName = surName;
        this.identificationNumber = identificationNumber;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return passengerID == passenger.passengerID
                && lastName.equals(passenger.lastName)
                && firstName.equals(passenger.firstName)
                && surName.equals(passenger.surName)
                && identificationNumber.equals(passenger.identificationNumber);
    }

    @Override
    public int hashCode() {
         return Objects.hash(passengerID, lastName, firstName, surName, identificationNumber);
    }

    public static class PassengerBuilder {
        private int passengerID;
        private String lastName;
        private String firstName;
        private String surName;
        private String identificationNumber;

        public PassengerBuilder setPassengerID(int passengerID) {
            this.passengerID = passengerID;
            return this;
        }

        public PassengerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PassengerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PassengerBuilder setSurName(String surName) {
            this.surName = surName;
            return this;
        }

        public PassengerBuilder setIdentificationNumber(String identificationNumber) {
            this.identificationNumber = identificationNumber;
            return this;
        }

        public Passenger createPassenger() {
            return new Passenger(passengerID, lastName, firstName, surName, identificationNumber);
        }
    }
}
