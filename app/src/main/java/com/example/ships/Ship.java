package com.example.ships;

public class Ship {
    int numberOfCells;
    boolean isShip;
    boolean hit;

    public Ship(int numberOfCells, boolean isShip) {
        this.numberOfCells = numberOfCells;
        this.isShip = isShip;
        this.hit=hit;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }

    public void setNumberOfCells(int numberOfCells) {
        this.numberOfCells = numberOfCells;
    }

    public boolean getisShip() {
        return isShip;
    }

    public void setShip(boolean isShip) {
        isShip = isShip;
    }
}
