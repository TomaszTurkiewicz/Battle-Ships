package com.example.ships.classes;

public class Winner {
    private String winner;
    private boolean outOfDate;
    private boolean surrendered;

    public Winner() {
        this.winner="";
        this.outOfDate=false;
        this.surrendered=false;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(boolean outOfDate) {
        this.outOfDate = outOfDate;
    }

    public boolean isSurrendered() {
        return surrendered;
    }

    public void setSurrendered(boolean surrendered) {
        this.surrendered = surrendered;
    }
}
