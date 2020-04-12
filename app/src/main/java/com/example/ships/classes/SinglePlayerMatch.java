package com.example.ships.classes;

import java.util.Arrays;
import java.util.List;

public class SinglePlayerMatch {
    private int difficulty;
    private boolean game;
    private List<Ship> battleFieldListMy = Arrays.asList(new Ship[100]);
    private BattleField battleFieldMy = new BattleField();
    private List<Ship> battleFieldListOpponent = Arrays.asList(new Ship[100]);
    private BattleField battleFieldOpponent = new BattleField();
    private boolean myTurn;

    public SinglePlayerMatch() {
        this.difficulty=-1;
        this.game=false;
        this.myTurn=false;
        fieldToList();
    }

    private void fieldToList() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                int index = 10*i+j;
                battleFieldListMy.set(index,battleFieldMy.getBattleField(i,j));
            }
        }
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                int index = 10*i+j;
                battleFieldListOpponent.set(index,battleFieldOpponent.getBattleField(i,j));
            }
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isGame() {
        return game;
    }

    public void setGame(boolean game) {
        this.game = game;
    }

    public List<Ship> getBattleFieldListMy() {
        return battleFieldListMy;
    }

    public void setBattleFieldListMy(List<Ship> battleFieldListMy) {
        this.battleFieldListMy = battleFieldListMy;
    }

    public List<Ship> getBattleFieldListOpponent() {
        return battleFieldListOpponent;
    }

    public void setBattleFieldListOpponent(List<Ship> battleFieldListOpponent) {
        this.battleFieldListOpponent = battleFieldListOpponent;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

}
