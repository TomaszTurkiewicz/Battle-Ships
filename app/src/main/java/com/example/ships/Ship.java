package com.example.ships;

public class Ship {
    int numberOfMasts;
    int shipNumber;
    boolean isShip;
    boolean isHit;


    public Ship(int numberOfMasts, int shipNumber, boolean isShip, boolean isHit) {
        this.numberOfMasts = numberOfMasts;
        this.shipNumber = shipNumber;
        this.isShip = isShip;
        this.isHit = isHit;
    }

    public int getNumberOfMasts() {
        return numberOfMasts;
    }

    public void setNumberOfMasts(int numberOfMasts) {
        this.numberOfMasts = numberOfMasts;
    }

    public int getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(int shipNumber) {
        this.shipNumber = shipNumber;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
