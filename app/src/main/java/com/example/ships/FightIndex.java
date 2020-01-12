package com.example.ships;

public class FightIndex {
    private boolean fight;
    private boolean accepted;
    private String opponent;
    private int counter;
    private String gameName;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public FightIndex(boolean fight, String opponent, boolean accepted, int counter, String gameName) {
        this.fight = fight;
        this.opponent = opponent;
        this.accepted = accepted;
        this.counter = counter;
        this.gameName = gameName;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public FightIndex() {
    }

    public boolean isFight() {
        return fight;
    }

    public void setFight(boolean fight) {
        this.fight = fight;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
