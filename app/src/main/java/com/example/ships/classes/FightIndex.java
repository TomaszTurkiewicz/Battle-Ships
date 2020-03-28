package com.example.ships.classes;

public class FightIndex {
    public String opponent;
    public boolean accepted;
    public String gameIndex;

    public FightIndex(String opponent, boolean accepted, String gameIndex) {
        this.opponent = opponent;
        this.accepted = accepted;
        this.gameIndex = gameIndex;
    }
    public FightIndex(){
        this.opponent="";
        this.accepted=false;
        this.gameIndex="";
    }

    public String getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(String gameIndex) {
        this.gameIndex = gameIndex;
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
