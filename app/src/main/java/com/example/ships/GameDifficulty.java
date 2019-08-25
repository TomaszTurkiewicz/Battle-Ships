package com.example.ships;

public class GameDifficulty {

    private int level;

    private static final GameDifficulty ourInstance = new GameDifficulty();

    public static GameDifficulty getInstance() {
        return ourInstance;
    }

    private GameDifficulty() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
