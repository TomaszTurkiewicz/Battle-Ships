package com.example.ships;

public class FightIndex {
    private boolean fight;
    private boolean accepted;
    private String opponent;



    public FightIndex(boolean fight, String opponent, boolean accepted) {
        this.fight = fight;
        this.opponent = opponent;
        this.accepted = accepted;
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
