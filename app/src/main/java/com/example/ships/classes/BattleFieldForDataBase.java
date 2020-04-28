package com.example.ships.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BattleFieldForDataBase {
    private boolean created;
    private long time;
    private Difficulty difficulty;
    private List<Ship> battleFieldList = Arrays.asList(new Ship[100]);
    private BattleField battleFieldField = new BattleField();
    private List<Integer> lastMove;

    public List<Integer> getLastMove() {
        return lastMove;
    }

    public void setLastMove(List<Integer> lastMove) {
        this.lastMove = lastMove;
    }

    public boolean isCreated() {
        return created;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public List<Ship> getBattleFieldList() {
        return battleFieldList;
    }

    public void setBattleFieldList(List<Ship> battleFieldList) {
        this.battleFieldList = battleFieldList;
    }

    public BattleFieldForDataBase() {
        this.created=false;
        fieldToList();
        this.difficulty = new Difficulty();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        this.time = calendar.getTimeInMillis();
        this.lastMove = new ArrayList<>();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public BattleField showBattleField(){
        return battleFieldField;
    }

    public void create() {
        battleFieldField.createFleet();
        fieldToList();
        created=true;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        this.time = calendar.getTimeInMillis();
    }

    public void fieldToList(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                int index = 10*i+j;
                battleFieldList.set(index,battleFieldField.getBattleField(i,j));
            }
        }
    }

    public void listToField(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleFieldField.makeShip(i,j,battleFieldList.get(10*i+j));
            }
        }
    }
}

