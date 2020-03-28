package com.example.ships.classes;

public class FightIndex {
    public String opponent;
    public boolean accepted;

    public FightIndex(String opponent, boolean accepted) {
        this.opponent = opponent;
        this.accepted = accepted;
    }
    public FightIndex(){
        this.opponent="";
        this.accepted=false;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
