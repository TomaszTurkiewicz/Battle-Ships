package com.example.ships.classes;

public class FightIndex {
    public String opponent;

    public FightIndex(String opponent) {
        this.opponent = opponent;
    }
    public FightIndex(){
        this.opponent="";
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
