package com.example.ships.classes;

import java.util.Arrays;
import java.util.List;

public class BattleFieldForDataBase {
    private boolean created;
    private Difficulty difficulty;
    private List<Ship> battleFieldList = Arrays.asList(new Ship[100]);
    private BattleField battleFieldField = new BattleField();

    public boolean isCreated() {
        return created;
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

