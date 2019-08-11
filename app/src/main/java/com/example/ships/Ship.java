package com.example.ships;

public class Ship {
   private   int numberOfMasts;
   private int shipNumber;
   private boolean isShip;
   private boolean isHit;


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

    public int isShipIntiger(){
        if (isShip()){
            return 1;
        }
        return 0;
    }

    public void setShip(boolean ship) {
        isShip = ship;
    }

    public boolean isHit() {
        return isHit;
    }

    public int isHitInteger(){
        if(isHit()){
            return 1;
        }
        return 0;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public void setShipInt(int i){
        if (i!=0){
            isShip = true;
        }
        else isShip=false;
    }

    public void setHitInt(int i){
        if (i!=0){
            isHit = true;
        }
        else isHit=false;
    }
}
