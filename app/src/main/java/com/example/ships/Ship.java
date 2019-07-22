package com.example.ships;

public class Ship {
    int numberOfCells;
    boolean isShip;


    public Ship(int numberOfCells, boolean isShip) {
        this.numberOfCells = numberOfCells;
        this.isShip = isShip;
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
