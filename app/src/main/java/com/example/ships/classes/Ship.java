package com.example.ships.classes;

public class Ship {
    private int numberOfMasts;
    private int shipNumber;
    private boolean isShip;
    private boolean isHit;
    private boolean isShipHit;


    public Ship(int numberOfMasts, int shipNumber, boolean isShip, boolean isHit, boolean isShipHit) {
        this.numberOfMasts = numberOfMasts;
        this.shipNumber = shipNumber;
        this.isShip = isShip;
        this.isHit = isHit;
        this.isShipHit = isShipHit;
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

    public boolean getIsShipHit(){
        return isShipHit;
    }
    public void setShipHit(boolean shipHit){
        isShipHit=shipHit;
    }
}
