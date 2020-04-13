package com.example.ships.classes;

import java.util.Arrays;
import java.util.List;

public class SinglePlayerMatch {
    private int difficulty;
    private boolean game;
    private List<Ship> battleFieldListMy;
    private List<Ship> battleFieldListOpponent;
    private boolean myTurn;

    public SinglePlayerMatch() {
        this.difficulty=-1;
        this.game=false;
        this.myTurn=false;
        this.battleFieldListMy = Arrays.asList(new Ship[100]);
        this.battleFieldListOpponent = Arrays.asList(new Ship[100]);
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setBattleFieldListMy(List<Ship> battleFieldListMy) {
        this.battleFieldListMy = battleFieldListMy;
    }

    public void setBattleFieldListOpponent(List<Ship> battleFieldListOpponent) {
        this.battleFieldListOpponent = battleFieldListOpponent;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
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


    public void setBattleFieldListMyFromArray(BattleField battleFieldMy) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                this.battleFieldListMy.set(i*10+j,battleFieldMy.getBattleField(i,j));
            }
        }
    }



    public List<Ship> getBattleFieldListOpponent() {
        return battleFieldListOpponent;
    }

    public void setBattleFieldListOpponentFromArray(BattleField battleFieldOpponent) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                this.battleFieldListOpponent.set(i*10+j,battleFieldOpponent.getBattleField(i,j));
            }
        }
    }

}
