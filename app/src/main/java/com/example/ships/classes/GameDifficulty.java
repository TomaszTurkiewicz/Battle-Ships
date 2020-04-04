package com.example.ships.classes;

public class GameDifficulty {

    private int level;
    private boolean random;
    private boolean multiplayerMode;


    public boolean isMultiplayerMode() {
        return multiplayerMode;
    }

    public void setMultiplayerMode(boolean multiplayerMode) {
        this.multiplayerMode = multiplayerMode;
    }


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

    public boolean getRandom(){
        return random;
    }
    public void setRandom(boolean random){
        this.random=random;
    }
}
