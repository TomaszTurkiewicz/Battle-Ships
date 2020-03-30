package com.example.ships.classes;

public class Difficulty {
    private boolean easy;
    private boolean isSet;

    public Difficulty(boolean easy, boolean isSet){
        this.easy = easy;
        this.isSet = isSet;
    }

    public Difficulty() {
        this.easy=false;
        this.isSet=false;
    }

    public boolean isEasy() {
        return easy;
    }

    public void setEasy(boolean easy) {
        this.easy = easy;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }
}
