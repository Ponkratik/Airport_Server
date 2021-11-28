package com.ponkratov.airport.server.model.entity;

public class Ticket implements Entity {
    private int ticketID;
    private int passengerID;
    private int flightID;
    private int seatNuber;
    private double price;
    private int baggageID;
    private boolean isRegistered;

    private Ticket(int ticketID, int passengerID, int flightID, int seatNuber, double price, int baggageID, boolean isRegistered) {
        this.ticketID = ticketID;
        this.passengerID = passengerID;
        this.flightID = flightID;
        this.seatNuber = seatNuber;
        this.price = price;
        this.baggageID = baggageID;
        this.isRegistered = isRegistered;
    }

    public int getTicketID() {
        return ticketID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getSeatNuber() {
        return seatNuber;
    }

    public double getPrice() {
        return price;
    }

    public int getBaggageID() {
        return baggageID;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public static class TicketBuilder {
        private int ticketID;
        private int passengerID;
        private int flightID;
        private int seatNuber;
        private double price;
        private int baggageID;
        private boolean isRegistered;

        public TicketBuilder setTicketID(int ticketID) {
            this.ticketID = ticketID;
            return this;
        }

        public TicketBuilder setPassengerID(int passengerID) {
            this.passengerID = passengerID;
            return this;
        }

        public TicketBuilder setFlightID(int flightID) {
            this.flightID = flightID;
            return this;
        }

        public TicketBuilder setSeatNuber(int seatNuber) {
            this.seatNuber = seatNuber;
            return this;
        }

        public TicketBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public TicketBuilder setBaggageID(int baggageID) {
            this.baggageID = baggageID;
            return this;
        }

        public TicketBuilder setRegistered(boolean registered) {
            isRegistered = registered;
            return this;
        }

        public Ticket createTicket() {
            return new Ticket(ticketID, passengerID, flightID, seatNuber, price, baggageID, isRegistered);
        }
    }
}
